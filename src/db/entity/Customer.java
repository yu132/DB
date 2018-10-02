package db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_customer")
public class Customer {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "customer_id")
	private Long customerID;
	
	@Column(name = "customer_phone",length = 30)
	private String customerPhone;
	
	@OneToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name = "customer_accoun_information")
	private AccountInformation customerAccountInformation;

	@OneToMany(cascade= {CascadeType.ALL})
	private List<CustomerReceivingInformation> customerReceivingInformation;

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public AccountInformation getCustomerAccountInformation() {
		return customerAccountInformation;
	}

	public void setCustomerAccountInformation(AccountInformation customerAccountInformation) {
		this.customerAccountInformation = customerAccountInformation;
	}

	public List<CustomerReceivingInformation> getCustomerReceivingInformation() {
		return customerReceivingInformation;
	}

	public void setCustomerReceivingInformation(List<CustomerReceivingInformation> customerReceivingInformation) {
		this.customerReceivingInformation = customerReceivingInformation;
	}
	
}
