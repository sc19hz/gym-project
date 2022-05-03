package gym.dao;

import org.springframework.data.repository.CrudRepository;

import gym.entity.Employee;

/**
 * @author Giant_Salted_Fish
 */
public interface EmployeeRepository extends CrudRepository<Employee, Integer>
{
	public Employee findByUserId(Integer userId);
}
