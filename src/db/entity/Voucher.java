package db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="voucher")
public class Voucher {
	
	private Integer id;
	private Integer restaurant_id;
	private Integer discount_money;
	private Integer need_to_use;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", length=11)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "restaurant_id", length = 11)
	public Integer getRestaurant_id() {
		return restaurant_id;
	}
	public void setRestaurant_id(Integer restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	@Column(name = "discount_money", length = 20)
	public Integer getDiscount_money() {
		return discount_money;
	}
	public void setDiscount_money(Integer discount_money) {
		this.discount_money = discount_money;
	}
	
	@Column(name = "need_to_use", length = 20)
	public Integer getNeed_to_use() {
		return need_to_use;
	}
	public void setNeed_to_use(Integer need_to_use) {
		this.need_to_use = need_to_use;
	}
	
}
