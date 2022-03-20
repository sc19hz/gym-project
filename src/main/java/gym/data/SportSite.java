package gym.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Representation of gym
 * 
 * @author Giant_Salted_Fish
 */
@Entity
public class SportSite
{
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * Name of the gym. Usually is the translation key.
	 */
	private String name;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public String getName() { return this.name; }
	
	public void setName(String name) { this.name = name; }
}
