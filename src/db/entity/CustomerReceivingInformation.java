package db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "customer_receiving_information")
public class CustomerReceivingInformation {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "customer_receiving_information_id")
	private Long customerReceivingInformationID;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_phone")
	private String customerPhone;
	
	@Column(name = "customer_address")
	private String customerAddress;

	public Long getCustomerReceivingInformationID() {
		return customerReceivingInformationID;
	}

	public void setCustomerReceivingInformationID(Long customerReceivingInformationID) {
		this.customerReceivingInformationID = customerReceivingInformationID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
	
	
}
