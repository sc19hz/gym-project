package gym;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gym.dao.UserRepository;
import gym.entity.User;
import gym.util.R;
import net.bytebuddy.utility.RandomString;

@RestController
public class MainController
{
	private static final String USER_ID = "uid";
	
	private static final int SESSION_SURVIVE_TIME = 30 * 60;
	
	@Autowired
	private UserRepository users;
	
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
		HttpServletRequest request,
		@RequestParam String email,
		@RequestParam String password,
		@RequestParam String salt
	) {
//		if(
//			email.length() > 64
//			|| password.length() != 
//			salt.length() != 4
//		) return R.error();
		
		return R.ok();
	}
	
	@PostMapping(path = "/login")
	public R login(
		HttpServletRequest request,
		@RequestParam String email,
		@RequestParam String password
	) {
		HttpSession session = request.getSession();
		if(session.getAttribute(USER_ID) != null)
			return R.error("ALREADY_LOGIN");
		
		User user = this.users.findByEmail(email);
		if(user == null)
			return R.error("WRONG_ACCOUNT");
		
		if(!user.getPassword().equals(password))
			return R.error("WRONG_PASSWORD");
		
		user.setLastLogin(new Date().getTime());
		user = this.users.save(user);
		
		session.setAttribute(USER_ID, user.getId());
		session.setMaxInactiveInterval(SESSION_SURVIVE_TIME);
		return R.ok().add("userId", Integer.toString(user.getId()));
	}
	
}
