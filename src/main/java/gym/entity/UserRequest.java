package gym.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user_request")
public class UserRequest
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	@Column(name = "manager_id", nullable = false)
	private Integer managerId;
	
	@Column(name = "reservation_id", nullable = false)
	private Integer reservationId;
	
	@Column(name = "description", nullable = false, length = 256)
	private String description;
	
	@Column(name = "make_time", nullable = false)
	private Long makeTime;
	
	public UserRequest() { }
	
	public UserRequest(Integer userId, Integer managerId, Integer reservationId) {
		this.userId = userId;
		this.managerId = managerId;
		this.reservationId = reservationId;
		this.description = "Not provide";
		this.makeTime = System.currentTimeMillis();
	}
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public Integer getManagerId() { return this.managerId; }
	
	public void setManagerId(Integer managerId) { this.managerId = managerId; }
	
	public Integer getReservationId() { return this.reservationId; }
	
	public void setReservationId(Integer reservationId) { this.reservationId = reservationId; }
	
	public String getDescription() { return this.description; }
	
	public void setDescription(String description) { this.description = description; }
	
	public Long getMakeTime() { return this.makeTime; }
	
	public void setMakeTime(Long makeTime) { this.makeTime = makeTime; }
}
