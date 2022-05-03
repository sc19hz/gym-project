package gym.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "venue")
public class Venue
{
	/**
	 * Default duration step is 1 hour
	 */
	public static final Integer DEF_TIME_STEP = 1000 * 60 * 60;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "display_name", nullable = false, length = 64)
	private String displayName;
	
	@Column(name = "icon", nullable = false, length = 256)
	private String icon;
	
	/**
	 * Default cost of this venue for one millisecond
	 */
	@Column(name = "fee", nullable = false)
	private Double fee;
	
	@Column(name = "vip_fee", nullable = false)
	private Double vipFee;
	
	/**
	 * The actual location of the venue
	 */
	@Column(name = "location", nullable = false, length = 256)
	private String location;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public String getDisplayName() { return this.displayName; }
	
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	
	public Double getFee() { return this.fee; }
	
	public void setFee(Double fee) { this.fee = fee; }
	
	public Double getVipFee() { return this.vipFee; }
	
	public void setVipFee(Double vipFee) { this.vipFee = vipFee; }
	
	public String getLocation() { return this.location; }
	
	public void setLocation(String location) { this.location = location; }
	
	public static Integer getDefTimeStep() { return DEF_TIME_STEP; }
}
