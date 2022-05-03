package gym.data;

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
	
	/**
	 * <pre>
	 * 0 = specified in year
	 * 1 = specified in month
	 * 2 = specified in week
	 * 3 = specified in day
	 * </pre>
	 */
	@Column(name = "type", nullable = false)
	private Integer type;
	
	@Column(name = "start_time", nullable = false)
	private Integer startTime;
	
	@Column(name = "duration", nullable = false)
	private Integer duration;
	
	@Column(name = "fee", nullable = false)
	private Double fee;
	
	@Column(name = "vip_fee", nullable = false)
	private Double vipFee;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getVenueId() { return this.venueId; }
	
	public void setVenueId(Integer venueId) { this.venueId = venueId; }
	
	public Integer getType() { return this.type; }
	
	public void setType(Integer type) { this.type = type; }
	
	public Integer getStartTime() { return this.startTime; }
	
	public void setStartTime(Integer startTime) { this.startTime = startTime; }
	
	public Integer getDuration() { return this.duration; }
	
	public void setDuration(Integer duration) { this.duration = duration; }
	
	public Double getFee() { return this.fee; }
	
	public void setFee(Double fee) { this.fee = fee; }
	
	public Double getVipFee() { return this.vipFee; }
	
	public void setVipFee(Double vipFee) { this.vipFee = vipFee; }
}
