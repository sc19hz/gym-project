package gym.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "topup_record")
public class TopupRecord
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	@Column(name = "make_time", nullable = false)
	private Long makeTime;
	
	@Column(name = "amount", nullable = false)
	private Double amount;
	
	@Column(name = "paid", nullable = false)
	private Double paid;
	
	public TopupRecord() { }
	
	public TopupRecord(Integer userId, Double amount, Double paid) {
		this.userId = userId;
		this.makeTime = System.currentTimeMillis();
		this.amount = amount;
		this.paid = paid;
	}
	
	public Integer getId() { return this.id; }
	
	public void setId(Integer id) { this.id = id; }
	
	public Integer getUserId() { return this.userId; }
	
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public Long getMakeTime() { return this.makeTime; }
	
	public void setMakeTime(Long makeTime) { this.makeTime = makeTime; }
	
	public Double getAmount() { return this.amount; }
	
	public void setAmount(Double amount) { this.amount = amount; }
	
	public Double getPaid() { return this.paid; }
	
	public void setPaid(Double paid) { this.paid = paid; }
}
