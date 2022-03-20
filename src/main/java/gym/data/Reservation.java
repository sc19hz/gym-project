package gym.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Represents a {@link User}'s reservation to a sport site including the time and duration
 *  
 * @author Giant_Salted_Fish
 */
@Entity
public class Reservation
{
	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer userId;
	
	private Integer
		sportSiteId,
		roomId,
		seatId;
	
	private Long
		date,
		duration;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public Integer getSportSiteId() { return this.sportSiteId; }
	
	public void setSportSiteId(Integer sportSiteId) { this.sportSiteId = sportSiteId; }
	
	public Integer getRoomId() { return this.roomId; }
	
	public void setRoomId(Integer roomId) { this.roomId = roomId; }
	
	public Integer getSeatId() { return this.seatId; }
	
	public void setSeatId(Integer seatId) { this.seatId = seatId; }
	
	public Long getDate() { return this.date; }
	
	public void setDate(Long date) { this.date = date; }
	
	public Long getDuration() { return this.duration; }
	
	public void setDuration(Long duration) { this.duration = duration; }
}
