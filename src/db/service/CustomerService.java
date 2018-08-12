package db.service;

import java.util.List;
import java.util.Map;

import db.entity.Customer;

public interface CustomerService {

	Map<String, Object> getRestaurantInformation(Customer customer, String restaurantName);

	Map<String, Object> getRecommendRestaurant(Customer customer, String userLocation);

	Map<String, Object> giveCommentToCarrier(Customer customer, String carrierUsername, String comment, Integer point);

	Map<String, Object> giveCommentToRestaurant(Customer customer, String restaurantUsername, String comment,
			Integer point);

	//»±…Ÿ≤À£¨∫‹√˜œ‘
	Map<String, Object> placeOrder(Customer customer,Long time, Long voucherID, Long restaurantID, Long customerReceivingInformationID,
			List<String> dishesID);

	Map<String, Object> register(String customerPhone, String username, String password, String nickname,
			String eMailAddress);

	Map<String, Object> addCustomerReceivingInformation(Customer customer, String customerName, String customerPhone,
			String customerAddress);

	Map<String, Object> deleteCustomerReceivingInformation(Customer customer,Long customerReceivingInformationID);

}