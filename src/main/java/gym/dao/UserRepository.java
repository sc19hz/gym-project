package gym.dao;

import org.springframework.data.repository.CrudRepository;

import gym.entity.User;

/**
 * @author Giant_Salted_Fish
 */
public interface UserRepository extends CrudRepository<User, Integer>
{
	public User findByEmail(String email);
}
