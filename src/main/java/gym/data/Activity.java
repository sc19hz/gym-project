package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity(name = "activity")
public class Activity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "activity_id", nullable = false)
	private Integer activityId;
	
	@Column(name = "activity_name", nullable = false, length = 64)
	private String activityName;
	
	@Column(name = "max_venue_number", nullable = false)
	private Integer maxVenueNumber;
	
	@Column(name = "current_status", nullable = false)
	private Boolean currentStatus;
	
	@Column(name = "location", nullable = false, length = 256)
	private String location;
	
	public Integer getActivityId() { return this.activityId; }
	
	public void setActivityId(Integer activityId) { this.activityId = activityId; }
	
	public String getActivityName() { return this.activityName; }
	
	public void setActivityName(String activityName) { this.activityName = activityName; }
	
	public Integer getMaxVenueNumber() { return this.maxVenueNumber; }
	
	public void setMaxVenueNumber(Integer maxVenueNumber) { this.maxVenueNumber = maxVenueNumber; }
	
	public Boolean getCurrentStatus() { return this.currentStatus; }
	
	public void setCurrentStatus(Boolean currentStatus) { this.currentStatus = currentStatus; }
	
	public String getLocation() { return this.location; }
	
	public void setLocation(String location) { this.location = location; }
}