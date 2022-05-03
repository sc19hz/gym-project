package gym.dao;

import org.springframework.data.repository.CrudRepository;

import gym.entity.Reservation;

/**
 * @author Giant_Salted_Fish
 */
public interface ReservationRepository extends CrudRepository<Reservation, Integer>
{

}
