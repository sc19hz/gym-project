package gym.dao;

import org.springframework.data.repository.CrudRepository;

import gym.entity.Reservable;

/**
 * @author Giant_Salted_Fish
 */
public interface ReservableRepository extends CrudRepository<Reservable, Integer>
{

}
