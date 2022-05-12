package gym.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A time block that you can reserve a venue
 * 
 * @author Giant_Salted_Fish
 */
@Entity(name = "time_block")
public class TimeBlock
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id = Integer.MAX_VALUE;
	
	@Column(name = "venue_id", nullable = false)
	private Integer venueId;
	
	@Column(name = "start_time", nullable = false)
	private Integer startTime;
	
	@Column(name = "end_time", nullable = false)
	private Integer endTime;
	
	@Column(name = "fee", nullable = false)
	private Double fee;
	
	@Column(name = "vip_fee", nullable = false)
	private Double vipFee;
	
	public TimeBlock() { }
	
	public TimeBlock(Integer venueId, Integer startTime, Integer endTime, Double fee)
	{
		this.venueId = venueId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.fee = fee;
		this.vipFee = fee;
	}
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getVenueId() { return this.venueId; }
	
	public void setVenueId(Integer venueId) { this.venueId = venueId; }
	
	public Integer getStartTime() { return this.startTime; }
	
	public void setStartTime(Integer startTime) { this.startTime = startTime; }
	
	public Integer getEndTime() { return this.endTime; }
	
	public void setEndTime(Integer endTime) { this.endTime = endTime; }
	
	public Double getFee() { return this.fee; }
	
	public void setFee(Double fee) { this.fee = fee; }
	
	public Double getVipFee() { return this.vipFee; }
	
	public void setVipFee(Double vipFee) { this.vipFee = vipFee; }
}
