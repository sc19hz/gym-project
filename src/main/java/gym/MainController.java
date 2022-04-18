package gym;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gym.data.Employee;
import gym.data.EmployeeRepository;
import gym.data.User;
import gym.data.UserRepository;

@Controller
public class MainController
{
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping(path = "/login")
	public String login(
		HttpServletRequest request,
		@RequestParam String user_email,
		@RequestParam String password
	) {
		// Check if user has already logged in
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(30 * 60);
		if(session.getAttribute("id") != null) return "redirect:/user";
		
		User user = this.userRepository.findByEmail(user_email);
		if(user == null)
		{
			// TODO: user not registered
		}
		
		if(!user.getPassword().equals(password))
		{
			// TODO: error password
		}
		
		session.setAttribute("id", user.getUserId());
		
		// Update last login time
		user.setLastLogin(new Date().getTime());
		this.userRepository.save(user);
		
		// Save user's id in session for later use
		return "redirect:/user";
	}
	
	@PostMapping(path = "/create_account")
	public String createAccount(
		HttpServletRequest request,
		@RequestParam String position,
		@RequestParam String password,
		@RequestParam String confirm_password,
		@RequestParam String email
	) {
		if(!password.equals(confirm_password))
		{
			// TODO: error confirmed password
		}
		
		User user = new User();
		user.setUsername("undefined"); // TODO
		user.setPicture("-");
		user.setEmail(email);
		user.setPassword(password);
		user.setUserStatus(false); // TODO
		user.setReservationStatus((byte)2); // TODO
		user.setAccount(0); // TODO
		user.setGender("?"); // TODO
		user.setHeight(0);
		user.setWeight(0);
		user.setTotalTrainingHours(0);
		
		// Do not forget to set login time
		user.setLastLogin(new Date().getTime());
		
		user = userRepository.save(user);
		request.getSession().setAttribute("id", user.getUserId());
		
		return "redirect:/user";
	}
	
	
	
//	@PostMapping(path = "/add")
//	public @ResponseBody String addNewUser(
//		@RequestParam String account,
//		@RequestParam String password,
//		@RequestParam String display
//	) {
//		// Create a new account
//		Employee n = new Employee();
//		
//		userRepository.save(n);
//		return "Ok";
//	}
//	
//	@GetMapping(path = "/all")
//	public @ResponseBody Iterable<Employee> getAllUsers()
//	{
//		// This returns a JSON or XML with the users
//		return userRepository.findAll();
//	}
}