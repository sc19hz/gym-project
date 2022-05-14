package gym.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gym.annotation.RequireAuth;
import gym.dao.ManagerRepository;
import gym.dao.ReservationRepository;
import gym.dao.TimeBlockRepository;
import gym.dao.TopupRecordRepository;
import gym.dao.UserRepository;
import gym.dao.UserRequestRepository;
import gym.dao.VenueRepository;
import gym.entity.Manager;
import gym.entity.Reservation;
import gym.entity.TimeBlock;
import gym.entity.TopupRecord;
import gym.entity.User;
import gym.entity.UserRequest;
import gym.entity.Venue;
import gym.service.JwtService;
import gym.util.R;
import net.bytebuddy.utility.RandomString;

@CrossOrigin
@RestController
public class UserController
{
	@Value("${user.deficon}")
	private String defUserIcon;
	
	@Value("${host.address}")
	private String host;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepository users;
	
	@Autowired
	private ManagerRepository managers;
	
	@Autowired
	private VenueRepository venues;
	
	@Autowired
	private TimeBlockRepository timeBlocks;
	
	@Autowired
	private ReservationRepository reservations;
	
	@Autowired
	private TopupRecordRepository topupRecords;
	
	@Autowired
	private UserRequestRepository userRequests;
	
	@GetMapping(path = "/hello")
	public Object hello() { return "Hello World!"; }
	
	@GetMapping(path = "/tmp")
	public Object tmp() {
		this.timeBlocks.save(
			new TimeBlock(
				4,
				13 * 60 * 60 * 1000,
				14 * 60 * 60 * 1000,
				9D
			)
		);
		this.timeBlocks.save(
			new TimeBlock(
				4,
				14 * 60 * 60 * 1000,
				15 * 60 * 60 * 1000,
				9D
			)
		);
		this.timeBlocks.save(
			new TimeBlock(
				4,
				15 * 60 * 60 * 1000,
				16 * 60 * 60 * 1000,
				9D
			)
		);
		
		return R.ok();
	}
	
	@GetMapping(path = "/debug")
	public Object debug() {
		String salt = RandomString.make(4);
		User u = new User(
			"debugger@email.com",
			DigestUtils.md5DigestAsHex((salt + "123456").getBytes()),
			salt,
			"Debugger",
			this.defUserIcon
		);
		
		u = this.users.save(u);
		
		Manager m = new Manager();
		m.setUserId(u.getId());
		this.managers.save(m);
		
		return R.ok();
	}
	
	@PostMapping(path = "/regis")
	public Object regis(
		HttpServletResponse response,
		@RequestParam String email,
		@RequestParam String password,
		@RequestParam(required = false) String displayName
	) {
		if(email.isEmpty())
			return R.raw(HttpStatus.BAD_REQUEST, "Email required!");
		
		User u = this.users.findByEmail(email);
		if(u != null)
			return R.raw(
				HttpStatus.FORBIDDEN.value(),
				"Email already registered, please try another one!"
			);
		
		if(password.isEmpty())
			return R.raw(HttpStatus.BAD_REQUEST, "Password required!");
		
		String salt = RandomString.make(4);
		boolean hasDisplay = displayName != null && displayName.length() > 0;
		u = this.users.save(
			new User(
				email,
				DigestUtils.md5DigestAsHex((salt + password).getBytes()),
				salt,
				hasDisplay ? displayName : "user_" + RandomString.make(8),
				this.defUserIcon
			)
		);
		
		return R.ok();
	}
	
	@PostMapping(path = "/login")
	public Object login(
		HttpServletResponse response,
		@RequestParam String email,
		@RequestParam String password
	) {
		User u = this.users.findByEmail(email);
		if(u == null)
			return R.raw(HttpStatus.BAD_REQUEST, "Email not find! please check and retry.");
		
		if(!DigestUtils.md5DigestAsHex((u.getSalt() + password).getBytes()).equals(u.getPassword()))
			return R.raw(HttpStatus.BAD_REQUEST, "Wrong password! please check and retry.");
		
		u.setLastLogin(System.currentTimeMillis());
		u = this.users.save(u);
		
		return R.ok().add("token", this.jwtService.genToken(u.getId()));
	}
	
	@RequireAuth
	@PostMapping(path = "/self")
	public Object selfInfo(HttpServletRequest request) {
		return this.processIconAddress(
			this.users.findById(this.jwtService.getUserId(request)).get()
		);
	}
	
	@RequireAuth
	@PostMapping(path = "/user/update-profile")
	public Object updateUserProfile(
		HttpServletRequest request,
		@RequestParam String displayName,
		@RequestParam float weight,
		@RequestParam float height,
		@RequestParam int gender
	) {
		User u = this.users.findById(this.jwtService.getUserId(request)).get();
		
		if(
			displayName.length() == 0
			|| weight <= 0F || weight > 500F
			|| height <= 0F || height > 300F
			|| gender < 0 || gender > 1
		) return R.raw(HttpStatus.BAD_REQUEST, "Invalid setting values!");
		
		u.setDisplayName(displayName);
		u.setWeight(weight);
		u.setHeight(height);
		u.setGender(gender);
		
		u = this.users.save(u);
		
		return R.ok();
	}
	
	@RequireAuth
	@PostMapping(path = "/home")
	public Object home(
		@RequestParam int pageNum,
		@RequestParam int pageSize,
		@RequestParam(required = false) String sort
	) {
		Page<Venue> page = this.venues.findAll(PageRequest.of(pageNum, pageSize));
		
		// TODO: sort by?
		
		return page.getContent();
	}
	
	@RequireAuth
	@PostMapping(path = "/venue/swiper-img")
	public Object getVenueSwiperImgs(@RequestParam int venueId) {
		List<String> list = new LinkedList<String>();
		list.add("http://127.0.0.1:8080/image/activity/swim.png");
		list.add("http://127.0.0.1:8080/image/activity/tennis.png");
		return list;
	}
	
	@RequireAuth
	@PostMapping(path = "/venue")
	public Object getVenue(@RequestParam int venueId) {
		return this.venues.findById(venueId).get();
	}
	
	@RequireAuth
	@PostMapping(path = "/venue-time-section")
	public Object venueTimeSection(@RequestParam int venueId) {
		List<TimeBlock> blocks = this.timeBlocks.findByVenueId(venueId);
		blocks.sort((a, b) -> a.getStartTime() - b.getStartTime());
		long startTime = this.getDateTime(0);
		long endTime = startTime + (
			blocks.size() > 0 ? blocks.get(blocks.size() - 1).getEndTime() : 0
		);
		startTime += blocks.size() > 0 ? blocks.get(0).getStartTime() : 0;
		return R.ok().add("startTime", startTime).add("endTime", endTime);
	}
	
	@RequireAuth
	@PostMapping(path = "/available-time-blocks")
	public Object availableTimeBlocks(
		@RequestParam int venueId,
		@RequestParam int reservableId,
		@RequestParam int day
	) {
		LinkedList<R> ret = new LinkedList<>();
		
		Venue v = this.venues.findById(venueId).get();
		long today = this.getDateTime(day);
		
		if(v.getCapacity() != -1) {
			// Venue site that like a swim pool, just count reservation number for each time block
			for(TimeBlock tb : this.timeBlocks.findByVenueId(venueId)) {
				Long blockStartTime = today + tb.getStartTime();
				Long blockEndTime   = today + tb.getEndTime();
				int num = this.reservations.countByVenueIdAndStartTimeLessThanAndEndTimeGreaterThan(
					venueId,
					blockEndTime,
					blockStartTime
				);
				
				ret.add(
					R.raw()
					.add("id", tb.getId())
					.add("startTime", blockStartTime)
					.add("endTime", blockEndTime)
					.add("capacity", v.getCapacity())
					.add("reserved", num)
					.add("fee", v.getFee())
				);
			}
		}
		else {
			
		}
		
		return ret;
	}
	
	@RequireAuth
	@PostMapping(path = "/venue-book")
	public Object venueBook(
		HttpServletRequest request,
		@RequestParam int venueId,
		@RequestParam int reservableId,
		@RequestParam int day,
		@RequestParam String timeBlocks
	) {
		LinkedList<TimeBlock> blocks = new LinkedList<>();
		String[] split = timeBlocks.split("_");
		for(
			int i = split.length;
			--i > 0;
			blocks.add(this.timeBlocks.findById(Integer.parseInt(split[i])).get())
		);
		
		blocks.sort((a, b) -> a.getStartTime() - b.getStartTime());
		double cost = 0D;
		for(TimeBlock tb : blocks) cost += tb.getFee();
		long today = this.getDateTime(day);
		
		// Check if amount is enough
		Integer userId = this.jwtService.getUserId(request);
		User user = this.users.findById(userId).get();
		if(user.getAmount() < cost)
			return R.raw(HttpStatus.BAD_REQUEST, "Amount left is not enough for the deal");
		user.setAmount(user.getAmount() - cost);
		this.users.save(user);
		
		// TODO: make sure that these are connected blocks
		// TODO: make sure these block are still available for reservation
		this.reservations.save(
			new Reservation(
				this.jwtService.getUserId(request),
				venueId,
				reservableId,
				today + blocks.getFirst().getStartTime(),
				today + blocks.getLast().getEndTime(),
				cost
			)
		);
		
		// Save top-up record
		this.topupRecords.save(new TopupRecord(userId, -cost, 0D));
		
		return R.ok();
	}
	
	@RequireAuth
	@PostMapping(path = "/reservations")
	public Object reservationRecords(HttpServletRequest request) {
		return this.reservations.findByUserId(
			this.jwtService.getUserId(request)
		);
	}
	
	@RequireAuth
	@PostMapping(path = "/reservation")
	public Object reservation(@RequestParam int reservationId) {
		return this.reservations.findById(reservationId);
	}
	
	@RequireAuth
	@PostMapping(path = "/top-up")
	public Object topup(HttpServletRequest request, @RequestParam int topupId) {
		final double[] topup = { 60, 120, 240 };
		Integer userId = this.jwtService.getUserId(request);
		User user = this.users.findById(userId).get();
		user.setAmount(user.getAmount() + topup[topupId]);
		this.users.save(user);
		
		final double[] paid = { 50, 100, 200 };
		this.topupRecords.save(new TopupRecord(userId, topup[topupId], paid[topupId]));
		
		return R.ok();
	}
	
	@RequireAuth
	@PostMapping(path = "/top-up-records")
	public Object topupRecords(HttpServletRequest request) {
		return this.topupRecords.findByUserId(this.jwtService.getUserId(request));
	}
	
	@RequireAuth
	@PostMapping(path = "/cancel-request")
	public Object cancelRequest(
		HttpServletRequest request,
		@RequestParam int reservationId
	) {
		this.userRequests.save(
			new UserRequest(
				this.jwtService.getUserId(request),
				this.venues.findById(
					this.reservations.findById(reservationId).get().getVenueId()
				).get().getManagerId(),
				reservationId
			)
		);
		return R.ok();
	}
	
//	public Object venueDetails(@RequestParam int venueId)
//	{
//		Venue v = this.venues.findById(venueId).get();
//		
//	}
	
	/**
	 * Add {@link #host} to user's icon path if it starts with '/'
	 */
	private User processIconAddress(User u)
	{
		String a = u.getIcon();
		u.setIcon(a.startsWith("/") ? this.host + a : a);
		return u;
	}
	
	private long getDateTime(int day)
	{
		final long HOUR = 60 * 60 * 1000;
		long time = System.currentTimeMillis();
		return time - time % (24 * HOUR) + (day * 24 + 16) * HOUR ;
	}
}
