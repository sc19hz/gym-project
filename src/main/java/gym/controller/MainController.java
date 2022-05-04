package gym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gym.annotation.RequireAuth;
import gym.dao.UserRepository;
import gym.entity.User;
import gym.service.JwtService;
import gym.util.R;
import net.bytebuddy.utility.RandomString;

@RestController
public class MainController
{
	private static final String USER_ID = "uid";
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepository users;
	
	@RequireAuth
	@GetMapping(path = "/hello")
	public Object hello() { return "Hello World!"; }
	
	@GetMapping(path = "/debug")
	public Object debug()
	{
		String salt = RandomString.make(4);
		User u = new User(
			"debugger@email.com",
			DigestUtils.md5DigestAsHex((salt + "123456").getBytes()),
			salt,
			"Debugger"
		);
		
		u = this.users.save(u);
		
		return R.ok("ready.").add("userId", u.getId().toString());
	}
	
	@PostMapping(path = "/regis")
	public Object regis(
		@RequestParam String email,
		@RequestParam String password,
		@RequestParam(required = false) String name
	) {
		if(email.isEmpty()) return R.error(400, "emailrequired");
		
		User u = this.users.findByEmail(email);
		if(u != null) return R.error(400, "userregistered");
		
		if(password.isEmpty()) return R.error(400, "passwordrequired");
		
		String salt = RandomString.make(4);
		u = this.users.save(
			new User(
				email,
				DigestUtils.md5DigestAsHex((salt + "123456").getBytes()),
				salt,
				name == null ? "user_" + RandomString.make(8) : name
			)
		);
		
		return R.ok();
	}
	
	@PostMapping(path = "/login")
	public Object login(
		@RequestParam String email,
		@RequestParam String password
	) {
		User u = this.users.findByEmail(email);
		if(u == null) return R.error(400, "usernotfound");
		
		if(!DigestUtils.md5DigestAsHex((u.getSalt() + password).getBytes()).equals(u.getPassword()))
			return R.error(400, "wrongpassword");
		
		return R.ok().add("token", this.jwtService.genToken(u.getId()));
	}
}
