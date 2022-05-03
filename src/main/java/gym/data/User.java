package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user")
public class User
{
	public static final String DEF_PICTURE = "?";
	
//	public static final String DEF_GENDER = "?";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "email", unique = true, nullable = false, length = 64)
	private String email;
	
	@Column(name = "password", nullable = false, length = 64)
	private String password;
	
	@Column(name = "display_name", nullable = false, length = 64)
	private String displayName;
	
	@Column(name = "icon", nullable = false, length = 256)
	private String icon;
	
	@Column(name = "last_login", nullable = false)
	private Long lastLogin;
	
	@Column(name = "amount", nullable = false)
	private Double amount;
	
	@Column(name = "gender", nullable = false)
	private Integer gender;
	
	@Column(name = "height", nullable = false)
	private Integer height;
	
	@Column(name = "weight", nullable = false)
	private Integer weight;
	
	@Column(name = "register_time", nullable = false)
	private Long registerTime;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public String getEmail() { return this.email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getPassword() { return this.password; }
	
	public void setPassword(String password) { this.password = password; }
	
	public String getDisplayName() { return this.displayName; }
	
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	
	public Long getLastLogin() { return this.lastLogin; }
	
	public void setLastLogin(Long lastLogin) { this.lastLogin = lastLogin; }
	
	public Double getAmount() { return this.amount; }
	
	public void setAmount(Double amount) { this.amount = amount; }
	
	public Integer getGender() { return this.gender; }
	
	public void setGender(Integer gender) { this.gender = gender; }
	
	public Integer getHeight() { return this.height; }
	
	public void setHeight(Integer height) { this.height = height; }
	
	public Integer getWeight() { return this.weight; }
	
	public void setWeight(Integer weight) { this.weight = weight; }
	
	public Long getRegisterTime() { return this.registerTime; }
	
	public void setRegisterTime(Long registerTime) { this.registerTime = registerTime; }
}
