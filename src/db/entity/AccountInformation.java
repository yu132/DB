package db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_account_information")
public class AccountInformation {
	
	@Id
	@Column(length = 30)
	private String username;
	
	@Column(length = 30)
	private String password;
	
	@Column(length = 30)
	private String nickname;
	
	@Column(name="e_mail_address",length = 30)
	private String eMailAddress;
	
	@Column(name = "activation_code",length=30)
	private String activationCode;
	
	@Column(name = "activation_state",length=10)
	private String activationState;
	
	@Column(name = "account_balance")
	private Double accountBalance;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getActivationState() {
		return activationState;
	}

	public void setActivationState(String activationState) {
		this.activationState = activationState;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String geteMailAddress() {
		return eMailAddress;
	}

	public void seteMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}	
}
