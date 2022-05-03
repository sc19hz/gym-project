package gym.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Smallest entity that can be reserved by users in a venue
 * 
 * @author Giant_Salted_Fish
 */
@Entity(name = "reservable")
public class Reservable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "venue_id", nullable = false)
	private Integer venueId;
	
	/**
	 * Default pattern is A0...An. Can be changed to display more useful information.
	 */
	@Column(name = "display_name", nullable = false, length = 64)
	private String displayName;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getVenueId() { return this.venueId; }
	
	public void setVenueId(Integer venueId) { this.venueId = venueId; }
	
	public String getDisplayName() { return this.displayName; }
	
	public void setDisplayName(String displayName) { this.displayName = displayName; }
}
