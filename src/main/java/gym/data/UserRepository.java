package gym.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Giant_Salted_Fish
 */
public interface UserRepository extends CrudRepository<User, Integer>
{
	public User findByEmail(String email);
}
