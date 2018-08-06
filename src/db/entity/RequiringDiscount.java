package db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "requiring_discount")
public class RequiringDiscount {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "requiring_discount_id")
	private Long requiringDiscountID;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	@Column(name = "requiring_money")
	private Double requiringMoney;
	
	@Column(name = "discount_money")
	private Double discountMoney;

	public Long getRequiringDiscountID() {
		return requiringDiscountID;
	}

	public void setRequiringDiscountID(Long requiringDiscountID) {
		this.requiringDiscountID = requiringDiscountID;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Double getRequiringMoney() {
		return requiringMoney;
	}

	public void setRequiringMoney(Double requiringMoney) {
		this.requiringMoney = requiringMoney;
	}

	public Double getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(Double discountMoney) {
		this.discountMoney = discountMoney;
	}
	
	
}
