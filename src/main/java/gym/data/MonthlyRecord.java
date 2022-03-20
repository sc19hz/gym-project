package gym.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Describes the statistics of a {@link User} playing sports in a specified {@link #month}
 * 
 * @author Giant_Salted_Fish
 */
@Entity
public class MonthlyRecord
{
	@Id
	@GeneratedValue()
	private Integer id;
	
	private Integer userId;
	
	private Integer month;
	
	private Integer
		swimHours,
		squashHours,
		fitnessHours;
	// TODO: validate if the total hours is the sum of the split hours?
	private Integer totalHours;
	
	@Override
	public String toString()
	{
		return String.format(
			"SportsRecord [id=%s, userId=%s, month=%s, swimHours=%s, squashHours=%s,"
				+ " fitnessHours=%s, totalHours=%s]",
			this.id, this.userId, this.month, this.swimHours, this.squashHours,
			this.fitnessHours, this.totalHours
		);
	}

	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public Integer getMonth() { return this.month; }
	
	public void setMonth(Integer month) { this.month = month; }
	
	public Integer getSwimHours() { return this.swimHours; }
	
	public void setSwimHours(Integer swimHours) { this.swimHours = swimHours; }
	
	public Integer getSquashHours() { return this.squashHours; }
	
	public void setSquashHours(Integer squashHours) { this.squashHours = squashHours; }
	
	public Integer getFitnessHours() { return this.fitnessHours; }
	
	public void setFitnessHours(Integer fitnessHours) { this.fitnessHours = fitnessHours; }
	
	public Integer getTotalHours() { return this.totalHours; }
	
	public void setTotalHours(Integer totalHours) { this.totalHours = totalHours; }
}
