package db.service;

import java.util.Map;

import db.entity.Carrier;

public interface CarrierService {

	Map<String, Object> getOrder(Carrier carrier);

	Map<String, Object> giveCommentToCustomer(Carrier carrier, String customerUsername, String comment, Integer point);

	Map<String, Object> register(String name, String phone, String username, String password, String nickname,
			String eMailAddress);

	Map<String, Object> start(Carrier carrier);

	Map<String, Object> stop(Carrier carrier);

	Map<String, Object> getAvailableOrder(Carrier carrier);

	Map<String, Object> getCustomerComment(Carrier carrier, String customerUsername);

}