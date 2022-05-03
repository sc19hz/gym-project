package gym.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user")
public class User
{
	public User() { }
	
	public User(String email, String password, String salt, String displayName)
	{
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.displayName = displayName;
		this.icon = "def_user_icon";
		this.lastLogin = 0L;
		this.amount = 0D;
		this.gender = 1;
		this.height = 1.75F;
		this.weight = 60F;
		this.registerTime = System.currentTimeMillis();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "email", unique = true, nullable = false, length = 64)
	private String email;
	
	@Column(name = "password", nullable = false, length = 64)
	private String password;
	
	@Column(name = "salt", nullable = false, length = 4)
	private String salt;
	
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
	private Float height;
	
	@Column(name = "weight", nullable = false)
	private Float weight;
	
	@Column(name = "register_time", nullable = false)
	private Long registerTime;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public String getEmail() { return this.email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getPassword() { return this.password; }
	
	public void setPassword(String password) { this.password = password; }
	
	public String getSalt() { return this.salt; }
	
	public void setSalt(String salt) { this.salt = salt; }
	
	public String getDisplayName() { return this.displayName; }
	
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	
	public String getIcon() { return this.icon; }
	
	public void setIcon(String icon) { this.icon = icon; }
	
	public Long getLastLogin() { return this.lastLogin; }
	
	public void setLastLogin(Long lastLogin) { this.lastLogin = lastLogin; }
	
	public Double getAmount() { return this.amount; }
	
	public void setAmount(Double amount) { this.amount = amount; }
	
	public Integer getGender() { return this.gender; }
	
	public void setGender(Integer gender) { this.gender = gender; }
	
	public Float getHeight() { return this.height; }
	
	public void setHeight(Float height) { this.height = height; }
	
	public Float getWeight() { return this.weight; }
	
	public void setWeight(Float weight) { this.weight = weight; }
	
	public Long getRegisterTime() { return this.registerTime; }
	
	public void setRegisterTime(Long registerTime) { this.registerTime = registerTime; }
}
