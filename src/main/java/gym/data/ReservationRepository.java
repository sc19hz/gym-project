package gym.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Giant_Salted_Fish
 */
public interface ReservationRepository extends CrudRepository<Reservation, Integer>
{
	public Iterable<Reservation> findAllByUserId(Integer userId);
	
	public Long deleteByUserId(Integer userId);
}
