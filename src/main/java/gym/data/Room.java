package gym.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Represents a room in a {@link SportSite}
 * 
 * @author Giant_Salted_Fish
 */
@Entity
public class Room
{
	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer sportSiteId;
	
	private String name;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getSportSiteId() { return this.sportSiteId; }
	
	public void setSportSiteId(Integer sportSiteId) { this.sportSiteId = sportSiteId; }
	
	public String getName() { return this.name; }
	
	public void setName(String name) { this.name = name; }
}
