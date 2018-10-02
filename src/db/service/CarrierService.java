package db.service;

import java.util.Map;

import db.entity.Carrier;

public interface CarrierService {

	Map<String, Object> getOrder(Carrier carrier);
	
	Map<String, Object> getOrderDetail(Carrier carrier,Long orderId);
	
	Map<String, Object> giveCommentToCustomer(Carrier carrier, Long customerId, String comment, Integer point);

	Map<String, Object> register(String name, String phone, String username, String password, String nickname,
			String eMailAddress);

	Map<String, Object> start(Carrier carrier);

	Map<String, Object> stop(Carrier carrier);

	Map<String, Object> getAvailableOrder(Carrier carrier, String location);

	Map<String, Object> getCustomerComment(Carrier carrier, Long customerId);
	
	Map<String, Object> takeOrder(Carrier carrier,Long orderId);
	
	Map<String, Object> getState(Carrier carrier);

	Map<String, Object> getInformation(Carrier carrier);
	
	Map<String, Object> changeInformation(Carrier carrier,String carrierPhone);
	
	Map<String, Object> getCommentToCustomer(Carrier carrier,Long customerId);
	
	Map<String, Object> changeOrderStateToGetDishes(Carrier carrier,Long orderId);
	
	Map<String, Object> changeOrderStateToFinish(Carrier carrier,Long orderId);
	
}