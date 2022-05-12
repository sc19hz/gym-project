package gym.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gym.entity.Venue;

/**
 * @author Giant_Salted_Fish
 */
public interface VenueRepository extends JpaRepository<Venue, Integer>
{

}
