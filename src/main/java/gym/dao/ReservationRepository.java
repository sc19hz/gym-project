package gym.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gym.entity.Reservation;

/**
 * @author Giant_Salted_Fish
 */
public interface ReservationRepository extends CrudRepository<Reservation, Integer>
{
	public List<Reservation> findByUserId(Integer userId);
	
	public List<Reservation> findByVenueId(Integer venueId);

	public long countByVenueIdAndMakeTimeGreaterThan(Integer venueId, Long time);
}
