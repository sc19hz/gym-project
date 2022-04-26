package gym.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Giant_Salted_Fish
 */
public interface ManagerRepository extends CrudRepository<Manager, Integer>
{
	public Optional<Manager> findByUserId(Integer userId);
}
