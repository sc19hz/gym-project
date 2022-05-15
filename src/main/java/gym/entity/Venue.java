package gym.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "venue")
public class Venue
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "manager_id", nullable = false)
	private Integer managerId;
	
	@Column(name = "activity_id", nullable = false)
	private Integer activityId;
	
	@Column(name = "display_name", nullable = false, length = 64)
	private String displayName;
	
	@Column(name = "icon", nullable = false, length = 256)
	private String icon;
	
	/**
	 * Capacity of the venue. Positive = sites like a swim pool. Negative = sites like a tennis play
	 * ground.
	 */
	@Column(name = "capacity", nullable = false)
	private Integer capacity;
	
	/**
	 * How many days that this venue allows its users to reserve ahead
	 */
	@Column(name = "days_allow", nullable = false)
	private Integer daysAllow;
	
	/**
	 * Default cost of this venue for one hour
	 */
	@Column(name = "fee", nullable = false)
	private Double fee = 5D;
	
	@Column(name = "vip_fee", nullable = false)
	private Double vipFee = 4D;
	
	/**
	 * The actual location of the venue
	 */
	@Column(name = "location", nullable = false, length = 256)
	private String location;
	
	public Venue() { }
	
	public Venue(
		Integer managerId,
		String displayName,
		String defIcon,
		String location,
		Integer activityId,
		Integer capacity,
		Integer daysAllow
	) {
		this.managerId = managerId;
		this.displayName = displayName;
		this.icon = defIcon;
		this.location = location;
		this.activityId = activityId;
		this.capacity = capacity;
		this.daysAllow = daysAllow;
	}
	
	public Venue processURL(String host) {
		this.icon = this.icon.startsWith("/") ? host + this.icon : this.icon;
		return this;
	}
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getManagerId() { return this.managerId; }
	
	public void setManagerId(Integer managerId) { this.managerId = managerId; }
	
	public Integer getActivityId() { return this.activityId; }
	
	public void setActivityId(Integer activityId) { this.activityId = activityId; }
	
	public String getDisplayName() { return this.displayName; }
	
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	
	public String getIcon() { return this.icon; }
	
	public void setIcon(String icon) { this.icon = icon; }
	
	public Integer getCapacity() { return this.capacity; }
	
	public void setCapacity(Integer capacity) { this.capacity = capacity; }
	
	public Integer getDaysAllow() { return this.daysAllow; }
	
	public void setDaysAllow(Integer daysAllow) { this.daysAllow = daysAllow; }
	
	public Double getFee() { return this.fee; }
	
	public void setFee(Double fee) { this.fee = fee; }
	
	public Double getVipFee() { return this.vipFee; }
	
	public void setVipFee(Double vipFee) { this.vipFee = vipFee; }
	
	public String getLocation() { return this.location; }
	
	public void setLocation(String location) { this.location = location; }
}
