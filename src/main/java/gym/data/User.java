package gym.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity
public class User
{
	@Id
	@GeneratedValue
	private Integer id;
	
	private String
		account,
		password;
	
	/**
	 * User's display name
	 */
	private String display;
	
	private Integer
		height = 170,
		weight = 70;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public String getAccount() { return this.account; }
	
	public void setAccount(String account) { this.account = account; }
	
	public String getPassword() { return this.password; }
	
	public void setPassword(String password) { this.password = password; }
	
	public String getDisplay() { return this.display; }
	
	public void setDisplay(String display) { this.display = display; }
	
	public Integer getHeight() { return this.height; }
	
	public void setHeight(Integer height) { this.height = height; }
	
	public Integer getWeight() { return this.weight; }
	
	public void setWeight(Integer weight) { this.weight = weight; }
}