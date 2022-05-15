package gym.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "employee")
public class Employee
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	@Column(name = "manager_id", nullable = false)
	private Integer managerId;
	
	@Column(name = "regis_time", nullable = false)
	private Long regisTime;
	
	public Employee() { }
	
	public Employee(Integer userId, Integer managerId)
	{
		this.userId = userId;
		this.managerId = managerId;
		this.regisTime = System.currentTimeMillis();
	}
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public Integer getManagerId() { return this.managerId; }
	
	public void setManagerId(Integer managerId) { this.managerId = managerId; }
	
	public Long getRegisTime() { return this.regisTime; }
	
	public void setRegisTime(Long regisTime) { this.regisTime = regisTime; }
}
