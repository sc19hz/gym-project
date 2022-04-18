package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity(name = "manager")
public class Manager
{
	@Id
	@GeneratedValue
	@Column(name = "manager_id", nullable = false)
	private int managerId;
	
	@Column(name = "email", nullable = false, length = 64)
	private String email;
	
	@Column(name = "manager_name", nullable = false, length = 64)
	private String managerName;
	
	@Column(name = "position", length = 256)
	private String position;
	
	public int getManagerId() { return this.managerId; }
	
	public void setManagerId(int managerId) { this.managerId = managerId; }
	
	public String getEmail() { return this.email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getManagerName() { return this.managerName; }
	
	public void setManagerName(String managerName) { this.managerName = managerName; }
	
	public String getPosition() { return this.position; }
	
	public void setPosition(String position) { this.position = position; }
}