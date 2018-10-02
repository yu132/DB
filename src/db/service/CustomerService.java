package db.service;

import java.util.List;
import java.util.Map;

import db.action.customerAction.PlaceOrder.MenuIdAndNumber;
import db.entity.Customer;

public interface CustomerService {

	Map<String, Object> getRestaurantInformation(Customer customer, String restaurantName);
	
	Map<String, Object> getRestaurantInformationById(Customer customer, Long id);

	Map<String, Object> getRecommendRestaurant(Customer customer, String userLocation);

	Map<String, Object> giveCommentToCarrier(Customer customer, Long carrierId, String comment, Integer point);

	Map<String, Object> giveCommentToRestaurant(Customer customer, Long restaurantId, String comment,
			Integer point);

	Map<String, Object> placeOrder(Customer customer,Long time, Long voucherID, Long restaurantID, Long customerReceivingInformationID,
			List<MenuIdAndNumber> dishes,Double money);

	Map<String, Object> register(String customerPhone, String username, String password, String nickname,
			String eMailAddress);

	Map<String, Object> addCustomerReceivingInformation(Customer customer, String customerName, String customerPhone,
			String customerAddress);

	Map<String, Object> deleteCustomerReceivingInformation(Customer customer,Long customerReceivingInformationID);
	
	Map<String, Object> getCustomerReceivingInformation(Customer customer);
	
	Map<String, Object> payOrder(Customer customer,Long orderId);
	
	Map<String, Object> checkMenu(Customer customer,List<MenuIdAndNumber> menuList,Long restaurantId);
	
	Map<String, Object> getOrder(Customer customer);
	
	Map<String, Object> getOrderDetail(Customer customer,Long orderId);
	
	Map<String, Object> getRestaurantComment(Customer customer,Long restaurantId);
	
	Map<String, Object> getCarrierComment(Customer customer,Long carrierId);
	
	Map<String, Object> getCommentToRestaurant(Customer customer,Long restaurantId);
	
	Map<String, Object> getCommentToCarrier(Customer customer,Long carrierId);
	
	Map<String, Object> getRestaurantInformationByIdWithoutMenu(Customer customer, Long id);
}