package gym.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gym.entity.Employee;

/**
 * @author Giant_Salted_Fish
 */
public interface EmployeeRepository extends CrudRepository<Employee, Integer>
{
	public Employee findByUserId(Integer userId);
	
	public List<Employee> findByManagerId(Integer managerId);
}
