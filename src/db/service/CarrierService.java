package db.service;

import java.util.Map;

public interface CarrierService {

	public Map<String,Object> getOrder(String username);
	
	public Map<String,Object> giveCommentToCustomer(String username,String customerUsername);
	
	public Map<String,Object> register(String name,String phone,String username,String password,String nickname,String eMailAddress);
	
	public Map<String,Object> start(String username);
	
	public Map<String,Object> stop(String username);
	
}
