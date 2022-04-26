package gym.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Giant_Salted_Fish
 */
public interface EmployeeRepository extends CrudRepository<Employee, Integer>
{
	public Optional<Employee> findByUserId(Integer userId);
}
