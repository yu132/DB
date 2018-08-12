package db.service;

import java.util.Map;

public interface RestaurantService {

	Map<String, Object> addMenu(String username, String menuName, Double menuPrice, Double menuDiscount);

	Map<String, Object> deleteMenu(String username, Long menuID);

	Map<String, Object> addVoucherActicity(String username, Double needPay, Double discountMoney, Double needToUse,
			Long validTime);

	Map<String, Object> deleteVoucherActicity(String username, Long voucherActicityID);

	Map<String, Object> open(String username);

	Map<String, Object> close(String username);

	Map<String, Object> getOrder(String username);

	Map<String, Object> GiveCommentToCustomer(String username, String customerUsername, String comment, Integer point);

	Map<String, Object> register(String restaurantName, String restaurantAddress, String restaurantPhone,
			String username, String password, String nickname, String eMailAddress);

	Map<String, Object> setRequiringDiscount(String username, Double requiringMoney, Double discountMoney);

	Map<String, Object> deleteRequiringDiscount(String username, Long requiringDiscountID);

}