package gym;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gym.data.User;
import gym.data.UserRepository;
import gym.util.R;

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
		return R.ok("ready.");
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
	
//	public R updatePassword()
	
	private <V> V requireLogin(HttpServletRequest request, WithUser<V> task)
	{
		Object uid = request.getSession().getAttribute(USER_ID);
		if(uid != null)
			try { return task.call(this.users.findById((Integer)uid).get()); }
			catch(Exception e) { }
		return null;
	}
	
//	private <V> V requireManage(HttpServletRequest request, Callable<V> task)
//	{
//		return null;
//	}
}
