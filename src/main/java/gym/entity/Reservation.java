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
	
	/**
	 * if reservable id != -1 then the reservation is for a entity based venue
	 */
	@Column(name = "reservable_id", nullable = false)
	private Integer reservableId;
	
	@Column(name = "make_time", nullable = false)
	private Long makeTime;
	
	@Column(name = "start_time", nullable = false)
	private Long startTime;
	
	@Column(name = "end_time", nullable = false)
	private Long endTime;
	
	@Column(name = "cost", nullable = false)
	private Double cost;
	
	@Column(name = "status", nullable = false)
	private Integer status;
	
	public Reservation() { }
	
	public Reservation(
		Integer userId,
		Integer venueId,
		Integer reservableId,
		Long startTime,
		Long endTime,
		Double cost
	) {
		this.userId = userId;
		this.venueId = venueId;
		this.reservableId = reservableId;
		this.makeTime = System.currentTimeMillis();
		this.startTime = startTime;
		this.endTime = endTime;
		this.cost = cost;
		this.status = 0;
	}
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public Integer getVenueId() { return this.venueId; }
	
	public void setVenueId(Integer venueId) { this.venueId = venueId; }
	
	public Integer getReservableId() { return this.reservableId; }
	
	public void setReservableId(Integer reservableId) { this.reservableId = reservableId; }
	
	public Long getStartTime() { return this.startTime; }
	
	public void setStartTime(Long startTime) { this.startTime = startTime; }
	
	public Long getEndTime() { return this.endTime; }
	
	public void setEndTime(Long endTime) { this.endTime = endTime; }
	
	public Double getCost() { return this.cost; }
	
	public void setCost(Double cost) { this.cost = cost; }
	
	public Integer getStatus() { return this.status; }
	
	public void setStatus(Integer status) { this.status = status; }
}
