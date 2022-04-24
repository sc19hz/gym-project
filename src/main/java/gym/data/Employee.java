package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity(name = "employ")
public class Employee
{
	@Id
	@GeneratedValue
	@Column(name = "employee_id", nullable = false)
	private Integer employeeId;
	
	@Column(name = "email", nullable = false, length = 64)
	private String email;
	
	@Column(name = "employee_name", nullable = false, length = 64)
	private String employeeName;
	
	@Column(name = "position", nullable = false, length = 64)
	private String position;
	
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	public Integer getEmployeeId() { return this.employeeId; }
	
	public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
	
	public String getEmail() { return this.email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getEmployeeName() { return this.employeeName; }
	
	public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
	
	public String getPosition() { return this.position; }
	
	public void setPosition(String position) { this.position = position; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
}