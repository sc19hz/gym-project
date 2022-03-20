package gym.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Sport types
 * 
 * @author Giant_Salted_Fish
 */
@Entity
public class Sport
{
	@Id
	@GeneratedValue
	private Integer sportId;
	
	private String name;
	
	public Integer getSportId() { return this.sportId; }

	public void setSportId(Integer sportId) { this.sportId = sportId; }

	public String getName() { return this.name; }

	public void setName(String name) { this.name = name; }	
}
