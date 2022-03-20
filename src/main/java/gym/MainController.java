package gym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gym.data.User;
import gym.data.UserRepository;

@Controller
@RequestMapping(path = "/gym")
public class MainController
{
	@Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addNewUser(
		@RequestParam String account,
		@RequestParam String password,
		@RequestParam String display
	) {
		// Create a new account
		User n = new User();
		n.setAccount(account);
		n.setPassword(password);
		n.setDisplay(display);
		
		userRepository.save(n);
		return "Ok";
	}
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<User> getAllUsers()
	{
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
}