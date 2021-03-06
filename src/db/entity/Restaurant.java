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
@Table(name = "t_restaurant")
public class Restaurant {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "restaurant_id")
	private Long restaurantID;
	
	@Column(name = "restaurant_name" ,length = 30)
	private String restaurantName;
	
	@Column(name = "restaurant_state" ,length = 10)
	private String restaurantState;
	
	@Column(name = "restaurant_address")
	private String restaurantAddress;
	
	@Column(name = "restaurant_phone" ,length = 30)
	private String restaurantPhone;
	
	@OneToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name = "account_information_id")
	private AccountInformation restaurantAccountInformation;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Menu> menus;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<VoucherActivity> voucherActivities;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<RequiringDiscount> requiringDiscounts;

	public Long getRestaurantID() {
		return restaurantID;
	}

	public void setRestaurantID(Long restaurantID) {
		this.restaurantID = restaurantID;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantState() {
		return restaurantState;
	}

	public void setRestaurantState(String restaurantState) {
		this.restaurantState = restaurantState;
	}

	public String getRestaurantAddress() {
		return restaurantAddress;
	}

	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}

	public String getRestaurantPhone() {
		return restaurantPhone;
	}

	public void setRestaurantPhone(String restaurantPhone) {
		this.restaurantPhone = restaurantPhone;
	}

	public AccountInformation getRestaurantAccountInformation() {
		return restaurantAccountInformation;
	}

	public void setRestaurantAccountInformation(AccountInformation restaurantAccountInformation) {
		this.restaurantAccountInformation = restaurantAccountInformation;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<VoucherActivity> getVoucherActivities() {
		return voucherActivities;
	}

	public void setVoucherActivities(List<VoucherActivity> voucherActivities) {
		this.voucherActivities = voucherActivities;
	}

	public List<RequiringDiscount> getRequiringDiscounts() {
		return requiringDiscounts;
	}

	public void setRequiringDiscounts(List<RequiringDiscount> requiringDiscounts) {
		this.requiringDiscounts = requiringDiscounts;
	}
	
}
