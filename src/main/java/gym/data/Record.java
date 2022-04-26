package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity(name = "record")
public class Record
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_id", nullable = false)
	private Integer recordId;
	
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	@Column(name = "month", nullable = false)
	private Integer month;
	
	@Column(name = "venue_id", nullable = false)
	private Integer venueId;
	
	@Column(name = "activity_length", nullable = false)
	private Integer activityLength;
	
	public Integer getRecordId() { return this.recordId; }
	
	public void setRecordId(Integer recordId) { this.recordId = recordId; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public Integer getMonth() { return this.month; }
	
	public void setMonth(Integer month) { this.month = month; }
	
	public Integer getVenueId() { return this.venueId; }
	
	public void setVenueId(Integer venueId) { this.venueId = venueId; }
	
	public Integer getActivityLength() { return this.activityLength; }
	
	public void setActivityLength(Integer activityLength) { this.activityLength = activityLength; }
}