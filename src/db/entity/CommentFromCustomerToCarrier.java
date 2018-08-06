package db.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "comment_from_customer_to_carrier")
public class CommentFromCustomerToCarrier {

	@Id
	@ManyToOne
	@JoinColumn(name = "carrier_id")
	private Carrier carrier;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	private String comment;

	private Integer point;

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
	
	public Carrier getCarrier() {
		return carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
