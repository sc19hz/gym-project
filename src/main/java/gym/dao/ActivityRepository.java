package gym.dao;

import org.springframework.data.repository.CrudRepository;

import gym.entity.Activity;

/**
 * @author Giant_Salted_Fish
 */
public interface ActivityRepository extends CrudRepository<Activity, Integer>
{
}
