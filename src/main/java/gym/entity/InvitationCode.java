package gym.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.bytebuddy.utility.RandomString;

/**
 * Invitation code that created by manager and send to his/her employees to register as an employee
 * 
 * @author Giant_Salted_Fish
 */
@Entity(name = "invitation_code")
public class InvitationCode
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "code", nullable = false, length = 256)
	private String code;
	
	@Column(name = "manager_id", nullable = false)
	private Integer managerId;
	
	@Column(name = "make_time", nullable = false)
	private Long makeTime;
	
	public InvitationCode() { }
	
	public InvitationCode(Integer managerId) {
		this.code = RandomString.make(4);
		this.managerId = managerId;
		this.makeTime = System.currentTimeMillis();
	}
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public String getCode() { return this.code; }
	
	public void setCode(String code) { this.code = code; }
	
	public Integer getManagerId() { return this.managerId; }
	
	public void setManagerId(Integer managerId) { this.managerId = managerId; }
	
	public Long getMakeTime() { return this.makeTime; }
	
	public void setMakeTime(Long makeTime) { this.makeTime = makeTime; }
}
