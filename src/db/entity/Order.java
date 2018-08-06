package db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "order")
public class Order {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "order_id")
	private Integer orderID;
	
	@Column(name = "order_time")
	private Long orderTime;
	
	@ManyToOne
	@JoinColumn(name = "voucher_id")
	private Voucher voucher;
	
	@Column(name = "orde_price")
	private Double orderPrice;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "customer_receiving_information_id")
	private CustomerReceivingInformation customerReceivingInformation;
	
	@ManyToOne
	@JoinColumn(name = "carrier_id")
	private Carrier carrier;
	
	@Column(name = "order_state",length=20)
	private String orderState;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Menu> dishes;

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
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

	public List<Menu> getDishes() {
		return dishes;
	}

	public void setDishes(List<Menu> dishes) {
		this.dishes = dishes;
	}
	
	
	
}
