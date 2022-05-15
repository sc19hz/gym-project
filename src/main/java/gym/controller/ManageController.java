package gym.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import gym.dao.TopupRecordRepository;
import gym.dao.UserRepository;
import gym.dao.UserRequestRepository;
import gym.dao.VenueRepository;
import gym.entity.Employee;
import gym.entity.InvitationCode;
import gym.entity.Reservation;
import gym.entity.TimeBlock;
import gym.entity.TopupRecord;
import gym.entity.User;
import gym.entity.UserRequest;
import gym.entity.Venue;
import gym.service.JwtService;
import gym.util.R;

@CrossOrigin
@RestController
public class ManageController
{
	@Value("${host.address}")
	private String host;
	
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
	
	@Autowired
	private UserRepository users;
	
	@Autowired
	private TopupRecordRepository topupRecords;
	
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
		Integer uid = this.jwtService.getUserId(request);
		if(
			this.employees.findByUserId(uid) != null
			|| this.managers.findByUserId(uid) != null
		) return R.raw(HttpStatus.BAD_REQUEST, "Your are already registered!");
		
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
		List<Venue> venues = this.venues.findByManagerId(this.getManagerId(request));
		for(Venue v : venues)
			v.processURL(this.host);
		return venues;
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
	@PostMapping(path = "/venue-create")
	public Object venueCreate(
		HttpServletRequest request,
		@RequestParam String name,
		@RequestParam String location,
		@RequestParam int sportId,
		@RequestParam int capacity,
		@RequestParam int daysAllow
	) {
		return R.ok().add(
			"id",
			this.venues.save(
				new Venue(
					this.getManagerId(request),
					name,
					"/image/activity/swim.png",
					location,
					sportId,
					capacity,
					daysAllow
				)
			).getId()
		);
	}
	
	@EmployeeAuth
	@Transactional
	@PostMapping(path = "/venue-del")
	public Object venueDel(@RequestParam int venueId) {
		this.venues.deleteById(venueId);
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
	
	@EmployeeAuth
	@PostMapping(path = "/reserved-venue")
	public Object reservedVenue(@RequestParam int reservationId) {
		return this.venues.findById(
			this.reservations.findById(reservationId).get().getVenueId()
		).get().processURL(this.host);
	}
	
	@EmployeeAuth
	@PostMapping(path = "/of-user")
	public Object ofUser(@RequestParam int userId) {
		return this.users.findById(userId).get();
	}
	
	@EmployeeAuth
	@Transactional
	@PostMapping(path = "/reject-request")
	public Object rejectRequest(@RequestParam int requestId) {
		this.userRequests.deleteById(requestId);
		return R.ok();
	}
	
	@EmployeeAuth
	@Transactional
	@PostMapping(path = "/accept-request")
	public Object acceptRequest(@RequestParam int requestId) {
		UserRequest req = this.userRequests.findById(requestId).get();
		
		Reservation res = this.reservations.findById(req.getReservationId()).get();
		res.setStatus(Reservation.CANCELED);
		this.reservations.save(res);
		
		this.topupRecords.save(new TopupRecord(req.getUserId(), res.getCost(), 0D));
		User u = this.users.findById(req.getUserId()).get();
		u.setAmount(u.getAmount() + res.getCost());
		this.users.save(u);
		
		this.userRequests.delete(req);
		
		return R.ok();
	}
	
	@EmployeeAuth
	@PostMapping(path = "/venue-reservations")
	public Object venueReservations(@RequestParam int venueId) {
		return this.reservations.findByVenueId(venueId);
	}
	
	@ManagerAuth
	@PostMapping(path = "/employee-list")
	public Object employeeList(HttpServletRequest request) {
		return this.employees.findByManagerId(this.getManagerId(request));
	}
	
	@ManagerAuth
	@Transactional
	@PostMapping(path = "/del-employee")
	public Object delEmployee(@RequestParam int employeeId) {
		this.employees.deleteById(employeeId);
		return R.ok();
	}
	
	private Integer getManagerId(HttpServletRequest request) {
		Integer uid = this.jwtService.getUserId(request);
		
		Employee e = this.employees.findByUserId(uid);
		return e != null ? e.getManagerId() : this.managers.findByUserId(uid).getId();
	}
}
