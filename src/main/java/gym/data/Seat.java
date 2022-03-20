package gym.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A usable seat in a {@link Room}
 * 
 * @author Giant_Salted_Fish
 */
@Entity
public class Seat
{
	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer roomId;
	
	private String name;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getRoomId() { return this.roomId; }
	
	public void setRoomId(Integer roomId) { this.roomId = roomId; }
	
	public String getName() { return this.name; }
	
	public void setName(String name) { this.name = name; }
}
