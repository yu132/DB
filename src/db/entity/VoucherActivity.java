package db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "voucher_activity")
public class VoucherActivity {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "voucher_activity_id")
	private Long voucherActivityID;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	@Column(name = "need_pay")
	private Double needPay;
	
	@Column(name = "discount_money")
	private Double discountMoney;
	
	@Column(name = "need_to_use")
	private Double needToUse;
	
	@Column(name = "valid_time")
	private Long validTime;

	public Long getVoucherActivityID() {
		return voucherActivityID;
	}

	public void setVoucherActivityID(Long voucherActivityID) {
		this.voucherActivityID = voucherActivityID;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Double getNeedPay() {
		return needPay;
	}

	public void setNeedPay(Double needPay) {
		this.needPay = needPay;
	}

	public Double getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(Double discountMoney) {
		this.discountMoney = discountMoney;
	}
	public Double getNeedToUse() {
		return needToUse;
	}

	public void setNeedToUse(Double needToUse) {
		this.needToUse = needToUse;
	}

	public Long getValidTime() {
		return validTime;
	}

	public void setValidTime(Long validTime) {
		this.validTime = validTime;
	}
	
	
}
