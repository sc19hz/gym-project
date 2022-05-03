package gym.dao;

import org.springframework.data.repository.CrudRepository;

import gym.entity.Manager;

/**
 * @author Giant_Salted_Fish
 */
public interface ManagerRepository extends CrudRepository<Manager, Integer>
{
	public Manager findByUserId(Integer userId);
}
