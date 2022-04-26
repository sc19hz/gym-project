package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity(name = "user")
public class User
{
	public static final String DEF_PICTURE = "?";
	
	public static final String DEF_GENDER = "?";
	
	public static final Integer
		ON_TIME = 0,
		OVERDUE = 1,
		WAITING = 2;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	@Column(name = "username", nullable = false, length = 64)
	private String username;
	
	@Column(name = "picture", nullable = false, length = 64)
	private String picture;
	
	@Column(name = "email", nullable = false, length = 64)
	private String email;
	
	@Column(name = "password", nullable = false, length = 64)
	private String password;
	
	@Column(name = "user_status", nullable = false)
	private Boolean userStatus;
	
	@Column(name = "last_login", nullable = false)
	private Long lastLogin;
	
	@Column(name = "reservation_status", nullable = false)
	private Integer reservationStatus;
	
	@Column(name = "amount", nullable = false)
	private Integer amount;
	
	@Column(name = "gender", nullable = false, length = 16)
	private String gender;
	
	@Column(name = "height", nullable = false)
	private Integer height;
	
	@Column(name = "weight", nullable = false)
	private Integer weight;
	
	@Column(name = "total_training_hours", nullable = false)
	private Integer totalTrainingHours;
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public String getUsername() { return this.username; }
	
	public void setUsername(String username) { this.username = username; }
	
	public String getPicture() { return this.picture; }
	
	public void setPicture(String picture) { this.picture = picture; }
	
	public String getEmail() { return this.email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getPassword() { return this.password; }
	
	public void setPassword(String password) { this.password = password; }
	
	public Boolean isUserStatus() { return this.userStatus; }
	
	public void setUserStatus(Boolean userStatus) { this.userStatus = userStatus; }
	
	public Long getLastLogin() { return this.lastLogin; }
	
	public void setLastLogin(Long lastLogin) { this.lastLogin = lastLogin; }
	
	public Integer getReservationStatus() { return this.reservationStatus; }
	
	public void setReservationStatus(Integer reservationStatus) { this.reservationStatus = reservationStatus; }
	
	public Integer getAmount() { return this.amount; }
	
	public void setAmount(Integer amount) { this.amount = amount; }
	
	public String getGender() { return this.gender; }
	
	public void setGender(String gender) { this.gender = gender; }
	
	public Integer getHeight() { return this.height; }
	
	public void setHeight(Integer height) { this.height = height; }
	
	public Integer getWeight() { return this.weight; }
	
	public void setWeight(Integer weight) { this.weight = weight; }
	
	public Integer getTotalTrainingHours() { return this.totalTrainingHours; }
	
	public void setTotalTrainingHours(Integer totalTrainingHours) { this.totalTrainingHours = totalTrainingHours; }
}