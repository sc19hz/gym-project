package gym.dao;

import org.springframework.data.repository.CrudRepository;

import gym.entity.Venue;

/**
 * @author Giant_Salted_Fish
 */
public interface VenueRepository extends CrudRepository<Venue, Integer>
{

}
