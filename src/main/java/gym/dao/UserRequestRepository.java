package gym.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gym.entity.UserRequest;

/**
 * @author Giant_Salted_Fish
 */
public interface UserRequestRepository extends CrudRepository<UserRequest, Integer>
{
	public List<UserRequest> findByManagerId(Integer managerId);
	
	public UserRequest findByReservationId(Integer reservationId);
}
