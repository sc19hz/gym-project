package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public String getCode() { return this.code; }
	
	public void setCode(String code) { this.code = code; }
}
