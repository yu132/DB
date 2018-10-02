package db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_order")
public class Order {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "order_id")
	private Long orderID;
	
	@Column(name = "order_time")
	private Long orderTime;
	
	@ManyToOne
	@JoinColumn(name = "voucher_id")
	private Voucher voucher;
	
	@Column(name = "orde_price")
	private Double orderPrice;
	
	@Column(name = "carrier_money")
	private Double carrierMoney;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "customer_receiving_information_id")
	private CustomerReceivingInformation customerReceivingInformation;
	
	@ManyToOne
	@JoinColumn(name = "carrier_id")
	private Carrier carrier;
	
	/**
	 * need pay, need carrier, to restaurant , get dishes, finish
	 */
	@Column(name = "order_state",length=20)
	private String orderState;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<MenuOrder> dishes;

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public Long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Long orderTime) {
		this.orderTime = orderTime;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public CustomerReceivingInformation getCustomerReceivingInformation() {
		return customerReceivingInformation;
	}

	public void setCustomerReceivingInformation(CustomerReceivingInformation customerReceivingInformation) {
		this.customerReceivingInformation = customerReceivingInformation;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	public List<MenuOrder> getDishes() {
		return dishes;
	}

	public void setDishes(List<MenuOrder> dishes) {
		this.dishes = dishes;
	}

	public Double getCarrierMoney() {
		return carrierMoney;
	}

	public void setCarrierMoney(Double carrierMoney) {
		this.carrierMoney = carrierMoney;
	}
	
}
