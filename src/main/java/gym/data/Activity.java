package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity(name = "activity")
public class Activity
{
	@Id
	@GeneratedValue
	@Column(name = "activity_id", nullable = false)
	private int activityId;
	
	@Column(name = "activity_name", nullable = false, length = 64)
	private String activityName;
	
	@Column(name = "max_venuenumber", nullable = false)
	private int maxVenuenumber;
	
	@Column(name = "current_venuenumber", nullable = false)
	private int currentVenuenumber;
	
	@Column(name = "current_status", nullable = false)
	private boolean currentStatus;
	
	public int getActivityId() { return this.activityId; }
	
	public void setActivityId(int activityId) { this.activityId = activityId; }
	
	public String getActivityName() { return this.activityName; }
	
	public void setActivityName(String activityName) { this.activityName = activityName; }
	
	public int getMaxVenuenumber() { return this.maxVenuenumber; }
	
	public void setMaxVenuenumber(int maxVenuenumber) { this.maxVenuenumber = maxVenuenumber; }
	
	public int getCurrentVenuenumber() { return this.currentVenuenumber; }
	
	public void setCurrentVenuenumber(int currentVenuenumber) { this.currentVenuenumber = currentVenuenumber; }
	
	public boolean isCurrentStatus() { return this.currentStatus; }
	
	public void setCurrentStatus(boolean currentStatus) { this.currentStatus = currentStatus; }
}