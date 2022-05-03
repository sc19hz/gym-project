package gym.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "reservation")
public class Reservation
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	@Column(name = "venue_id", nullable = false)
	private Integer venueId;
	
	@Column(name = "make_time", nullable = false)
	private Long makeTime;
	
	@Column(name = "start_time", nullable = false)
	private Long startTime;
	
	@Column(name = "duration", nullable = false)
	private Integer duration;
	
	@Column(name = "cost", nullable = false)
	private Double cost;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public Integer getVenueId() { return this.venueId; }
	
	public void setVenueId(Integer venueId) { this.venueId = venueId; }
	
	public Long getStartTime() { return this.startTime; }
	
	public void setStartTime(Long startTime) { this.startTime = startTime; }
	
	public Integer getDuration() { return this.duration; }
	
	public void setDuration(Integer duration) { this.duration = duration; }
	
	public Double getCost() { return this.cost; }
	
	public void setCost(Double cost) { this.cost = cost; }
}
