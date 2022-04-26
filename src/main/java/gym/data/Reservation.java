package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Giant_Salted_Fish
 */
@Entity(name = "reservation")
public class Reservation
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservation_id", nullable = false)
	private Integer reservationId;
	
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	@Column(name = "venue_id", nullable = false)
	private Integer venueId;
	
	@Column(name = "reservation_start_time", nullable = false)
	private Long reservationStartTime;
	
	@Column(name = "reservation_end_time", nullable = false)
	private Long reservationEndTime;
	
	@Column(name = "reservation_make_time", nullable = false)
	private Long reservationMakeTime;
	
	public Integer getReservationId() { return this.reservationId; }
	
	public void setReservationId(Integer reservationId) { this.reservationId = reservationId; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public Integer getVenueId() { return this.venueId; }
	
	public void setVenueId(Integer venueId) { this.venueId = venueId; }
	
	public Long getReservationStartTime() { return this.reservationStartTime; }
	
	public void setReservationStartTime(Long reservationStartTime) { this.reservationStartTime = reservationStartTime; }
	
	public Long getReservationEndTime() { return this.reservationEndTime; }
	
	public void setReservationEndTime(Long reservationEndTime) { this.reservationEndTime = reservationEndTime; }
	
	public Long getReservationMakeTime() { return this.reservationMakeTime; }
	
	public void setReservationMakeTime(Long reservationMakeTime) { this.reservationMakeTime = reservationMakeTime; }
}