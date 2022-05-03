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
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
}
