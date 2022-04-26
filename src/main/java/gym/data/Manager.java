package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity(name = "manager")
public class Manager
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manager_id", nullable = false)
	private Integer managerId;
	
	@Column(name = "manager_name", nullable = false, length = 64)
	private String managerName;
	
	@Column(name = "position", nullable = false, length = 64)
	private String position;
	
	@Column(name = "user_id", nullable = false, unique = true)
	private Integer userId;
	
	public Integer getManagerId() { return this.managerId; }
	
	public void setManagerId(Integer managerId) { this.managerId = managerId; }
	
	public String getManagerName() { return this.managerName; }
	
	public void setManagerName(String managerName) { this.managerName = managerName; }
	
	public String getPosition() { return this.position; }
	
	public void setPosition(String position) { this.position = position; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
}