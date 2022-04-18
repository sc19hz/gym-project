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
	private int venueId;
	
	@Column(name = "activity_id", nullable = false)
	private int activityId;
	
	@Column(name = "current_venuenumber", nullable = false)
	private int currentVenuenumber;
	
	@Column(name = "available", nullable = false)
	private boolean available;
	
	public int getVenueId() { return this.venueId; }
	
	public void setVenueId(int venueId) { this.venueId = venueId; }
	
	public int getActivityId() { return this.activityId; }
	
	public void setActivityId(int activityId) { this.activityId = activityId; }
	
	public int getCurrentVenuenumber() { return this.currentVenuenumber; }
	
	public void setCurrentVenuenumber(int currentVenuenumber) { this.currentVenuenumber = currentVenuenumber; }
	
	public boolean isAvailable() { return this.available; }
	
	public void setAvailable(boolean available) { this.available = available; }
}