package gym;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gym.data.Activity;
import gym.data.ActivityRepository;
import gym.data.Employee;
import gym.data.EmployeeRepository;
import gym.data.ManagerRepository;
import gym.data.Reservation;
import gym.data.ReservationRepository;
import gym.data.User;
import gym.data.UserRepository;
import gym.data.Venue;
import gym.data.VenueRepository;

@RestController
public class MainController
{
	private static final String USER_ID = "uid";
	
	private static final int SESSION_SURVIVE_TIME = 30 * 60;
	
	private static final String
		OK = "OK",
		LOGIN_REQUIRED = "LOGIN_REQUIRED",
		LOGOUT_REQUIRED = "LOGOUT_REQUIRED",
		ERROR_ACCOUNT = "ERROR_ACCOUNT",
		ERROR_PASSWORD = "ERROR_PASSWORD",
		INSUFFICIENT_PERMISSION = "INSUFFICIENT_PERMISSION",
		ERROR_OPERATE_TARGET = "ERROR_OPERATE_TARGET";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private VenueRepository venueRepository;
	
	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@PostMapping(path = "/login")
	public String login(
		HttpServletRequest request,
		@RequestParam String user_email,
		@RequestParam String password
	) {
		// If user has already login, redirect to user page
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(SESSION_SURVIVE_TIME);
		if(session.getAttribute(USER_ID) != null) return LOGOUT_REQUIRED;
		
		// Validate email and password
		User user = this.userRepository.findByEmail(user_email);
		if(user == null) return ERROR_ACCOUNT;
		
		if(!user.getPassword().equals(password)) return ERROR_PASSWORD;
		session.setAttribute(USER_ID, user.getUserId());
		
		// Update last login time
		user.setLastLogin(new Date().getTime());
		this.userRepository.save(user);
		
		return OK;
	}
	
	@PostMapping(path = "/create_account")
	public String createAccount(
		HttpServletRequest request,
		@RequestParam String position,
		@RequestParam String password,
		@RequestParam String confirm_password,
		@RequestParam String email
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;
		
		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		if(!password.equals(confirm_password)) return ERROR_PASSWORD;
		
		User newUser = new User();
		newUser.setUsername("undefined"); // TODO
		newUser.setPicture(User.DEF_PICTURE);
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setUserStatus(true);
		newUser.setLastLogin((long)0);
		newUser.setReservationStatus(User.WAITING);
		newUser.setAmount(0);
		newUser.setGender(User.DEF_GENDER); // TODO
		newUser.setHeight(0);
		newUser.setWeight(0);
		newUser.setTotalTrainingHours(0);
		
		return Integer.toString(this.userRepository.save(newUser).getUserId());
	}
	
	@Transactional
	@PostMapping(path = "/del_user")
	public String delUser(
		HttpServletRequest request,
		@RequestParam Integer user_id
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;
		
		if(user_id == uid) return LOGOUT_REQUIRED;
		
		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		Optional<User> target = this.userRepository.findById(user_id);
		if(!target.isPresent()) return ERROR_OPERATE_TARGET;
		
		// Delete all relevant data of this user
		this.reservationRepository.deleteByUserId(user_id);
		// TODO: delete records
		this.userRepository.delete(target.get());
		
		return OK;
	}
	
	@PostMapping(path = "/sort_user")
	public List<User> sortUser(
		HttpServletRequest request,
		@RequestParam String type
	) {
		LinkedList<User> result = new LinkedList<>();
		
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return result;
		
		if(!this.ensureOperator(uid)) return result;
		
		for(User u : this.userRepository.findAll())
			result.add(u);
		switch(type)
		{
		case "BY_TIME":
			result.sort((u0, u1) -> (int)(u0.getLastLogin() - u1.getLastLogin()));
			break;
		default:;
		}
		
		return result;
	}
	
	@PostMapping(path = "/search_user")
	public List<User> searchUser(
		HttpServletRequest request,
		@RequestParam String user_input
	) {
		LinkedList<User> result = new LinkedList<>();
		
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return result;
		
		if(!this.ensureOperator(uid)) return result;
		
		for(User u : this.userRepository.findAll())
			if(u.getUsername().contains(user_input) || u.getEmail().contains(user_input))
				result.add(u);
		
		return result;
	}
	
	@PostMapping(path = "/edit_reservation")
	public String editReservation(
		HttpServletRequest request,
		@RequestParam Integer reservation_id,
		@RequestParam Integer venue_id,
		@RequestParam Long reservation_start_time,
		@RequestParam Long reservation_end_time
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;
		
		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		Optional<Reservation> target = this.reservationRepository.findById(reservation_id);
		if(!target.isPresent()) return ERROR_OPERATE_TARGET;
		
		Optional<Venue> v = this.venueRepository.findById(venue_id);
		if(!v.isPresent()) return ERROR_OPERATE_TARGET;
		
		Reservation r = target.get();
		r.setVenueId(venue_id);
		r.setReservationStartTime(reservation_start_time);
		r.setReservationEndTime(reservation_end_time);
		this.reservationRepository.save(r);
		
		return OK;
	}
	
	@PostMapping(path = "/edit_user_account")
	public String editUserAccount(
		HttpServletRequest request,
		Integer user_id,
		Integer reservation_status,
		Integer amount
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;
		
		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		Optional<User> target = this.userRepository.findById(user_id);
		if(!target.isPresent()) return ERROR_OPERATE_TARGET;
		
		User user = target.get();
		user.setReservationStatus(reservation_status);
		user.setAmount(amount);
		this.userRepository.save(user);
		
		return OK;
	}
	
	@PostMapping(path = "/add_sport")
	public String addSport(
		HttpServletRequest request,
		@RequestParam String activity_name,
		@RequestParam Integer max_venue_number,
		@RequestParam String location
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;

		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		Activity ac = new Activity();
		ac.setActivityName(activity_name);
		ac.setCurrentStatus(false);
		ac.setMaxVenueNumber(max_venue_number);
		ac.setLocation(location);
		
		Integer acId = this.activityRepository.save(ac).getActivityId();
		
		// Create corresponding venues
		ArrayList<Venue> venues = new ArrayList<>(max_venue_number);
		for(int i = max_venue_number; i-- > 0; )
		{
			Venue v = new Venue();
			v.setActivityId(acId);
			// TODO: set a max capacity maybe
			
			venues.add(v);
		}
		this.venueRepository.saveAll(venues);
		
		return Integer.toString(acId);
	}
	
	@Transactional
	@PostMapping(path = "/del_sport")
	public String delSport(
		HttpServletRequest request,
		@RequestParam Integer activity_id
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;
		
		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		Optional<Activity> target = this.activityRepository.findById(activity_id);
		if(!target.isPresent()) return ERROR_OPERATE_TARGET;
		
		this.activityRepository.delete(target.get());
		// TODO: move activity to recycled place
		// TODO: move all relevant venues
		
		return OK;
	}
	
	@PostMapping(path = "/bool_sport")
	public String boolSport(
		HttpServletRequest request,
		@RequestParam Integer activity_id
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;
		
		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		Optional<Activity> target = this.activityRepository.findById(activity_id);
		if(!target.isPresent()) return ERROR_OPERATE_TARGET;
		
		Activity ac = target.get();
		ac.setCurrentStatus(ac.getCurrentStatus());
		this.activityRepository.save(ac);
		
		return OK;
	}
	
	@PostMapping(path = "/edit_sport")
	public String editSport(
		HttpServletRequest request,
		@RequestParam Integer activity_id,
		@RequestParam String activity_name,
		@RequestParam Integer max_venue_number,
		@RequestParam String location
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;
		
		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		Optional<Activity> target = this.activityRepository.findById(activity_id);
		if(!target.isPresent()) return ERROR_OPERATE_TARGET;
		
		Activity ac = target.get();
		ac.setActivityName(activity_name);
		ac.setMaxVenueNumber(max_venue_number);
		// TODO: what is current venue number for?
		ac.setLocation(location);
		this.activityRepository.save(ac);
		
		return OK;
	}
	
	@PostMapping(path = "/search_sport")
	public List<Activity> searchSport(
		HttpServletRequest request,
		@RequestParam String user_input
	) {
		LinkedList<Activity> result = new LinkedList<>();
		
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return result;
		
		if(!this.ensureOperator(uid)) return result;
		
		for(Activity ac : this.activityRepository.findAll())
			if(ac.getActivityName().contains(user_input))
				result.add(ac);
		
		return result;
	}
	
	@PostMapping(path = "/add_venue")
	public String addVenue(
		HttpServletRequest request,
		@RequestParam Integer activity_id
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;
		
		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		Venue v = new Venue();
		v.setActivityId(activity_id);
		
		return Integer.toString(this.venueRepository.save(v).getVenueId());
	}
	
	@Transactional
	@PostMapping(path = "/del_venue")
	public String delVenue(
		HttpServletRequest request,
		@RequestParam Integer venue_id
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;
		
		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		Optional<Venue> target = this.venueRepository.findById(venue_id);
		if(!target.isPresent()) return ERROR_OPERATE_TARGET;
		
		Venue v = target.get();
		this.venueRepository.delete(v);
		// TODO: move this venue to a backup repository maybe
		
		return OK;
	}
	
	@Transactional
	@PostMapping(path = "/del_employee")
	public String delEmployee(
		HttpServletRequest request,
		@RequestParam Integer employee_id
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;
		
		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		Optional<Employee> target = this.employeeRepository.findById(employee_id);
		if(!target.isPresent()) return ERROR_OPERATE_TARGET;
		
		this.employeeRepository.delete(target.get());
		
		return OK;
	}
	
	@PostMapping(path = "/edit_employee")
	public String editEmployee(
		HttpServletRequest request,
		@RequestParam Integer employee_id,
		@RequestParam String email,
		@RequestParam String employee_name
	) {
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return LOGIN_REQUIRED;
		
		if(!this.ensureOperator(uid)) return INSUFFICIENT_PERMISSION;
		
		Optional<Employee> target = this.employeeRepository.findById(employee_id);
		if(!target.isPresent()) return ERROR_OPERATE_TARGET;
		
		Employee em = target.get();
		em.setEmail(email);
		em.setEmployeeName(employee_name);
		this.employeeRepository.save(em);
		
		return OK;
	}
	
	@PostMapping(path = "/search_employee")
	public List<Employee> searchEmployee(
		HttpServletRequest request,
		@RequestParam String user_input
	) {
		LinkedList<Employee> result = new LinkedList<>();
		
		HttpSession session = request.getSession();
		Integer uid = (Integer)session.getAttribute(USER_ID);
		if(uid == null) return result;
		
		if(!this.ensureOperator(uid)) return result;
		
		for(Employee e : this.employeeRepository.findAll())
			if(e.getEmployeeName().contains(user_input))
				result.add(e);
		
		return result;
	}
	
	private boolean ensureOperator(Integer id)
	{
		return(
			this.employeeRepository.findById(id).isPresent()
			|| this.managerRepository.findById(id).isPresent()
		);
	}
}