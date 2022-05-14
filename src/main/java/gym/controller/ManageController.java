package gym.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gym.annotation.EmployeeAuth;
import gym.annotation.ManagerAuth;
import gym.annotation.RequireAuth;
import gym.dao.ActivityRepository;
import gym.dao.EmployeeRepository;
import gym.dao.InvitationCodeRepository;
import gym.dao.ManagerRepository;
import gym.dao.ReservationRepository;
import gym.dao.TimeBlockRepository;
import gym.dao.UserRequestRepository;
import gym.dao.VenueRepository;
import gym.entity.Employee;
import gym.entity.InvitationCode;
import gym.entity.Reservation;
import gym.entity.TimeBlock;
import gym.entity.Venue;
import gym.service.JwtService;
import gym.util.R;

@CrossOrigin
@RestController
public class ManageController
{
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ManagerRepository managers;
	
	@Autowired
	private EmployeeRepository employees;
	
	@Autowired
	private InvitationCodeRepository invitationCodes;
	
	@Autowired
	private VenueRepository venues;
	
	@Autowired
	private ActivityRepository activities;
	
	@Autowired
	private TimeBlockRepository timeBlocks;
	
	@Autowired
	private ReservationRepository reservations;
	
	@Autowired
	private UserRequestRepository userRequests;
	
	@RequireAuth
	@PostMapping(path = "/check-authority")
	public Object login(HttpServletRequest request) {
		Integer userId = this.jwtService.getUserId(request);
		
		boolean isManager = this.managers.findByUserId(userId) != null;
		boolean isEmployee = this.employees.findByUserId(userId) != null;
		
		return R.ok().add("authority", isManager ? "manager" : isEmployee ? "employee" : "user");
	}
	
	@RequireAuth
	@PostMapping(path = "/check-invitation")
	public Object checkInvitation(
		HttpServletRequest request,
		@RequestParam String invitationCode
	) {
		InvitationCode code = this.invitationCodes.findByCode(invitationCode);
		if(code == null)
			return R.raw(HttpStatus.BAD_REQUEST, "Invalid invitation code, please check and retry!");
		
		this.invitationCodes.delete(code);
		
		// Create an employee for corresponding user
		this.employees.save(new Employee(this.jwtService.getUserId(request), code.getManagerId()));
		return R.ok();
	}
	
	@ManagerAuth
	@PostMapping(path = "/gen-invitation-code")
	public Object genInvitationCode(HttpServletRequest request) {
		return this.invitationCodes.save(
			new InvitationCode(
				this.managers.findByUserId(
					this.jwtService.getUserId(request)
				).getId()
			)
		);
	}
	
	@EmployeeAuth
	@PostMapping(path = "/manage-venue")
	public Object venues(HttpServletRequest request) {
		return this.venues.findByManagerId(this.getManagerId(request));
	}
	
	@EmployeeAuth
	@PostMapping(path = "/sports")
	public Object sports() {
		return this.activities.findAll();
	}
	
	@EmployeeAuth
	@PostMapping(path = "/venue-edit")
	public Object venueEdit(
		@RequestParam int venueId,
		@RequestParam String name,
		@RequestParam String location,
		@RequestParam int sportId,
		@RequestParam int capacity,
		@RequestParam int daysAllow
	) {
		Venue v = this.venues.findById(venueId).get();
		v.setDisplayName(name);
		v.setLocation(location);
		v.setActivityId(sportId);
		v.setCapacity(capacity);
		v.setDaysAllow(daysAllow);
		
		this.venues.save(v);
		
		return R.ok();
	}
	
	@EmployeeAuth
	@PostMapping(path = "/time-blocks")
	public Object timeBlocks(@RequestParam int venueId) {
		return this.timeBlocks.findByVenueId(venueId);
	}
	
	@EmployeeAuth
	@Transactional
	@PostMapping(path = "/delete-time-blocks")
	public Object deleteTimeBlocks(@RequestParam int venueId) {
		this.timeBlocks.deleteByVenueId(venueId);
		return R.ok();
	}
	
	@EmployeeAuth
	@PostMapping(path = "/add-time-block")
	public Object addTimeBlock(
		@RequestParam int venueId,
		@RequestParam int startTime,
		@RequestParam int endTime,
		@RequestParam double price
	) {
		this.timeBlocks.save(new TimeBlock(venueId, startTime, endTime, price));
		return R.ok();
	}
	
	@EmployeeAuth
	@PostMapping(path = "/total-payments")
	public Object totalPayments(HttpServletRequest request) {
		double total = 0D;
		for(Venue v : this.venues.findByManagerId(this.getManagerId(request)))
			for(Reservation r : this.reservations.findByVenueId(v.getId()))
				if(r.getStatus() != 2)
					total += r.getCost();
		return R.ok().add("val", total);
	}
	
	@EmployeeAuth
	@PostMapping(path = "/user-request-list")
	public Object userRequestList(HttpServletRequest request) {
		return this.userRequests.findByManagerId(this.getManagerId(request));
	}
	
	private Integer getManagerId(HttpServletRequest request) {
		Integer uid = this.jwtService.getUserId(request);
		
		Employee e = this.employees.findByUserId(uid);
		return e != null ? e.getManagerId() : this.managers.findByUserId(uid).getId();
	}
}
