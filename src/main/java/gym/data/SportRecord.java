package gym.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Record that generated after {@link User} complete a sport(usually is the reservation). Holds the
 * user id, sport site id(gym),  room id, seat id, sport id, time and duration.
 * 
 * @author Giant_Salted_Fish
 */
@Entity
public class SportRecord
{
	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer userId;
	
	private Integer
		sportSiteId,
		roomId,
		seatId;
	
	private Integer sportId;
	
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
	
	public Integer getSportId() { return this.sportId; }
	
	public void setSportId(Integer sportId) { this.sportId = sportId; }
	
	public Long getDate() { return this.date; }
	
	public void setDate(Long date) { this.date = date; }
	
	public Long getDuration() { return this.duration; }
	
	public void setDuration(Long duration) { this.duration = duration; }
}
