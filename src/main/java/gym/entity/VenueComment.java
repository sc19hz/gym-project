package gym.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Comments for various venues that made by users
 * 
 * @author Giant_Salted_Fish
 */
@Entity(name = "venue_comment")
public class VenueComment
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	/**
	 * Id of the user that sent this comment
	 */
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	@Column(name = "venue_id", nullable = false)
	private Integer venueId;
	
	@Column(name = "post_time", nullable = false)
	private Long postTime;
	
	@Column(name = "comment", nullable = false, length = 256)
	private String comment;
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public Integer getVenueId() { return this.venueId; }
	
	public void setVenueId(Integer venueId) { this.venueId = venueId; }
	
	public Long getPostTime() { return this.postTime; }
	
	public void setPostTime(Long postTime) { this.postTime = postTime; }
	
	public String getComment() { return this.comment; }
	
	public void setComment(String comment) { this.comment = comment; }
}
