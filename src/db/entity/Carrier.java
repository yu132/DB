package db.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_carrier")
public class Carrier {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "carrier_id")
	private Long carrierID;
	
	@Column(name = "carrier_name",length = 30)
	private String carrierName;
	
	@Column(name = "carrier_state" ,length = 10)
	private String carrierState;
	
	@Column(name = "carrier_phone" ,length = 30)
	private String carrierPhone;
	
	@OneToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name = "carrier_account_information")
	private AccountInformation carrierAccountInformation;


	public Long getCarrierID() {
		return carrierID;
	}

	public void setCarrierID(Long carrierID) {
		this.carrierID = carrierID;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getCarrierState() {
		return carrierState;
	}

	public void setCarrierState(String carrierState) {
		this.carrierState = carrierState;
	}

	public String getCarrierPhone() {
		return carrierPhone;
	}

	public void setCarrierPhone(String carrierPhone) {
		this.carrierPhone = carrierPhone;
	}

	public AccountInformation getCarrierAccountInformation() {
		return carrierAccountInformation;
	}

	public void setCarrierAccountInformation(AccountInformation carrierAccountInformation) {
		this.carrierAccountInformation = carrierAccountInformation;
	}
	
	
	
}
