package db.service;

import java.util.Map;

import db.entity.Restaurant;

public interface RestaurantService {

	Map<String, Object> addMenu(Restaurant restaurant, String menuName, Double menuPrice, Double menuDiscount);

	Map<String, Object> deleteMenu(Restaurant restaurant, Long menuID);

	Map<String, Object> addVoucherActicity(Restaurant restaurant, Double needPay, Double discountMoney, Double needToUse,
			Long validTime);

	Map<String, Object> deleteVoucherActicity(Restaurant restaurant, Long voucherActicityID);

	Map<String, Object> open(Restaurant restaurant);

	Map<String, Object> close(Restaurant restaurant);

	Map<String, Object> GiveCommentToCustomer(Restaurant restaurant, String customerUsername, String comment, Integer point);

	Map<String, Object> register(String restaurantName, String restaurantAddress, String restaurantPhone,
			String username, String password, String nickname, String eMailAddress);

	Map<String, Object> setRequiringDiscount(Restaurant restaurant, Double requiringMoney, Double discountMoney);

	Map<String, Object> deleteRequiringDiscount(Restaurant restaurant, Long requiringDiscountID);
	
	Map<String, Object> getMenu(Restaurant restaurant);
	
	Map<String, Object> getOrder(Restaurant restaurant);
	
	Map<String, Object> getOrderDetail(Restaurant restaurant,Long orderId);
	
	Map<String, Object> getState(Restaurant restaurant);
	
	Map<String, Object> changeInformation(Restaurant restaurant,String restaurantName,String restaurantAddress,String restaurantPhone);
	
	Map<String, Object> getInformation(Restaurant restaurant);
}