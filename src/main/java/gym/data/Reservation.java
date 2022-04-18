package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity(name = "reservation")
public class Reservation
{
	@Id
	@GeneratedValue
	@Column(name = "reservation_id", nullable = false)
	private int reservationId;
	
	@Column(name = "user_id", nullable = false)
	private int userId;
	
	@Column(name = "activity_id", nullable = false)
	private int activityId;
	
	@Column(name = "reservation_start_time", nullable = false)
	private long reservationStartTime;
	
	@Column(name = "reservation_end_time", nullable = false)
	private long reservationEndTime;
	
	@Column(name = "venue_id", nullable = false)
	private int venue_id;
	
	public int getReservationId() { return this.reservationId; }
	
	public void setReservationId(int reservationId) { this.reservationId = reservationId; }
	
	public int getUserId() { return this.userId; }
	
	public void setUserId(int userId) { this.userId = userId; }
	
	public int getActivityId() { return this.activityId; }
	
	public void setActivityId(int activityId) { this.activityId = activityId; }
	
	public long getReservationStartTime() { return this.reservationStartTime; }
	
	public void setReservationStartTime(long reservationStartTime) { this.reservationStartTime = reservationStartTime; }
	
	public long getReservationEndTime() { return this.reservationEndTime; }
	
	public void setReservationEndTime(long reservationEndTime) { this.reservationEndTime = reservationEndTime; }
	
	public int getVenue_id() { return this.venue_id; }
	
	public void setVenue_id(int venue_id) { this.venue_id = venue_id; }
}