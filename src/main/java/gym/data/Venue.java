package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity(name = "venue")
public class Venue
{
	@Id
	@GeneratedValue
	@Column(name = "venue_id", nullable = false)
	private Integer venueId;
	
	@Column(name = "activity_id", nullable = false)
	private Integer activityId;
	
//	@Column(name = "current_venuenumber", nullable = false)
//	private Integer currentVenuenumber;
	
	public Integer getVenueId() { return this.venueId; }
	
	public void setVenueId(Integer venueId) { this.venueId = venueId; }
	
	public Integer getActivityId() { return this.activityId; }
	
	public void setActivityId(Integer activityId) { this.activityId = activityId; }
}