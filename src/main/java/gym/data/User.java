package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity(name = "user")
public class User
{
	@Id
	@GeneratedValue
	@Column(name = "user_id", nullable = false)
	private int userId;
	
	@Column(name = "username", nullable = false, length = 64)
	private String username;
	
	@Column(name = "picture", nullable = false, length = 64)
	private String picture;
	
	@Column(name = "email", nullable = false, length = 64)
	private String email;
	
	@Column(name = "password", nullable = false, length = 64)
	private String password;
	
	@Column(name = "user_status", nullable = false)
	private boolean userStatus;
	
	@Column(name = "last_login", nullable = false)
	private long lastLogin;
	
	@Column(name = "reservation_status", nullable = false)
	private byte reservationStatus;
	
	@Column(name = "account", nullable = false)
	private int account;
	
	@Column(name = "gender", nullable = false, length = 16)
	private String gender;
	
	@Column(name = "height", nullable = false)
	private int height;
	
	@Column(name = "weight", nullable = false)
	private int weight;
	
	@Column(name = "total_training_hours", nullable = false)
	private int totalTrainingHours;
	
	public int getUserId() { return this.userId; }
	
	public void setUserId(int userId) { this.userId = userId; }
	
	public String getUsername() { return this.username; }
	
	public void setUsername(String username) { this.username = username; }
	
	public String getPicture() { return this.picture; }
	
	public void setPicture(String picture) { this.picture = picture; }
	
	public String getEmail() { return this.email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getPassword() { return this.password; }
	
	public void setPassword(String password) { this.password = password; }
	
	public boolean isUserStatus() { return this.userStatus; }
	
	public void setUserStatus(boolean userStatus) { this.userStatus = userStatus; }
	
	public long getLastLogin() { return this.lastLogin; }
	
	public void setLastLogin(long lastLogin) { this.lastLogin = lastLogin; }
	
	public byte getReservationStatus() { return this.reservationStatus; }
	
	public void setReservationStatus(byte reservationStatus) { this.reservationStatus = reservationStatus; }
	
	public int getAccount() { return this.account; }
	
	public void setAccount(int account) { this.account = account; }
	
	public String getGender() { return this.gender; }
	
	public void setGender(String gender) { this.gender = gender; }
	
	public int getHeight() { return this.height; }
	
	public void setHeight(int height) { this.height = height; }
	
	public int getWeight() { return this.weight; }
	
	public void setWeight(int weight) { this.weight = weight; }
	
	public int getTotalTrainingHours() { return this.totalTrainingHours; }
	
	public void setTotalTrainingHours(int totalTrainingHours) { this.totalTrainingHours = totalTrainingHours; }
}