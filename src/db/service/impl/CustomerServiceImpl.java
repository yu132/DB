package db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import db.dao.AccountInformationDao;
import db.dao.CarrierDao;
import db.dao.CommentFromCustomerToCarrierDao;
import db.dao.CommentFromCustomerToRestaurantDao;
import db.dao.CustomerDao;
import db.dao.CustomerReceivingInformationDao;
import db.dao.OrderDao;
import db.dao.RestaurantDao;
import db.dao.VoucherDao;
import db.entity.AccountInformation;
import db.entity.Carrier;
import db.entity.Customer;
import db.entity.CustomerReceivingInformation;
import db.entity.Order;
import db.entity.Restaurant;
import db.entity.Voucher;
import db.util.randomstr.RandomStr;

public class CustomerServiceImpl {
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private RestaurantDao restaurantDao;
	
	@Resource
	private UserServiceImpl userServiceImpl;
	
	@Resource
	private CustomerDao customerDao;
	
	@Resource
	private CarrierDao carrierDao;
	
	@Resource
	private VoucherDao voucherDao;
	
	@Resource
	private	AccountInformationDao accountInformationDao;
	
	@Resource
	private CommentFromCustomerToCarrierDao commentFromCustomerToCarrierDao;
	
	@Resource
	private CommentFromCustomerToRestaurantDao commentFromCustomerToRestaurantDao;

	@Resource
	private CustomerReceivingInformationDao customerReceivingInformationDao;
	
	public Map<String,Object> getRestaurantInformation(String username,String restaurantName){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;	
		}if(restaurantName==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantName is null");
			return result;	
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(username)) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		List<Restaurant> restaurant=restaurantDao.geRestaurantByFuzzyName(restaurantName);
		
		result.put("Result", "Success");
		result.put("RestaurantInformation", restaurant);
		return result;
	}
	
	public Map<String,Object> getRecommendRestaurant(String username,String userLocation){
	Map<String,Object> result=new HashMap<>();
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;	
		}if(userLocation==null) {
			result.put("Result", "Error");
			result.put("Reason", "UserLocation is null");
			return result;	
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(username)) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		//有关位置的我都先不做
		
		return result;
	}
	
	public Map<String,Object> giveCommentToCarrier(String username,String carrierUsername,String comment,Integer point){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}if(carrierUsername==null) {
			result.put("Result", "Error");
			result.put("Reason", "CarrierUsername is null");
			return result;
		}if(comment==null) {
			result.put("Result", "Error");
			result.put("Reason", "Comment is null");
			return result;
		}if(point==null) {
			result.put("Result", "Error");
			result.put("Reason", "Point is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(username)) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		//加上只有接过单才能给评论的限制
		List<Order> orderlist=orderDao.getOrderByCarrierUsernameAndCustomerUsername(carrierUsername, username);
		
		if(orderlist.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to comment on this customer");
			return result;
		}
		
		Carrier carrier=carrierDao.getCarrierByUsername(carrierUsername);
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier's username is incorrect");
			return result;
		}
		
		Customer customer=customerDao.getCustomerByUsername(username);
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is incorrect");
			return result;
		}
	
		commentFromCustomerToCarrierDao.addCommentFromCustomerToCarrier(customer,carrier, comment, point);
	
		result.put("Result", "Success");
		
		return result;
	}
	
	public Map<String,Object> giveCommentToRestaurant(String username,String restaurantUsername,String comment,Integer point){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}if(restaurantUsername==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantUsername is null");
			return result;
		}if(comment==null) {
			result.put("Result", "Error");
			result.put("Reason", "Comment is null");
			return result;
		}if(point==null) {
			result.put("Result", "Error");
			result.put("Reason", "Point is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(username)) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		//加上只有接过单才能给评论的限制
		List<Order> orderlist=orderDao.getOrderByRestaurantUsernameAndCustomerUsername(restaurantUsername, username);
		
		if(orderlist.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to comment on this customer");
			return result;
		}
		
		Restaurant restaurant=restaurantDao.getRestaurantByUsername(restaurantUsername);
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant's username is incorrect");
			return result;
		}
		
		Customer customer=customerDao.getCustomerByUsername(username);
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is incorrect");
			return result;
		}
	
		commentFromCustomerToRestaurantDao.addCommentFromCustomerToRestaurant(customer,restaurant, comment, point);
	
		result.put("Result", "Success");
		
		return result;
	}
	
	public Map<String,Object> placeOrder(Long time,Long voucherID,Double orderPrice,Long restaurantID,Long customerReceivingInformationID){
		Map<String,Object> result=new HashMap<>();
		
		if(time==null) {
			result.put("Result", "Error");
			result.put("Reason", "Time is null");
			return result;
		}if(orderPrice==null) {
			result.put("Result", "Error");
			result.put("Reason", "OrderPrice is null");
			return result;
		}if(restaurantID==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantID is null");
			return result;
		}if(customerReceivingInformationID==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerReceivingInformationID is null");
			return result;
		}
		
		Order order=new Order();
		
		Voucher voucher=null;
		
		if(voucherID!=null) {
			voucher=voucherDao.getVoucherById(voucherID);
			if(voucher==null) {
				result.put("Result", "Error");
				result.put("Reason", "VoucherID is incorrect");
				return result;
			}
		}
		
		Restaurant restaurant=restaurantDao.getRestaurantById(restaurantID);
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantID is incorrect");
			return result;
		}
		
		CustomerReceivingInformation customerReceivingInformation=customerReceivingInformationDao.getCustomerReceivingInformationById(customerReceivingInformationID);
		
		if(customerReceivingInformation==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerReceivingInformationID is incorrect");
			return result;
		}
		
		order.setOrderTime(time);
		order.setVoucher(voucher);
		order.setOrderPrice(orderPrice);
		order.setRestaurant(restaurant);
		order.setCustomerReceivingInformation(customerReceivingInformation);
		order.setCarrier(null);
		order.setOrderState("Need carrier");
		
		result.put("Result", "Success");
		
		return result;
	}
	
	public Map<String,Object> register(String customerPhone,String username,String password,String nickname,String eMailAddress){
		
		Map<String,Object> result=new HashMap<>();
		
		if(customerPhone==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerPhone is null");
			return result;
		}if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}if(password==null) {
			result.put("Result", "Error");
			result.put("Reason", "Password is null");
			return result;
		}if(nickname==null) {
			result.put("Result", "Error");
			result.put("Reason", "Nickname is null");
			return result;
		}if(eMailAddress==null) {
			result.put("Result", "Error");
			result.put("Reason", "EMailAddress is null");
			return result;
		}
		
		if(userServiceImpl.checkUsernameExist(username)) {
			result.put("Result", "Error");
			result.put("Reason", "Username exists");
			return result;
		}
		
		String activationCode=RandomStr.randomStr(10);
		
		AccountInformation accountInformation=new AccountInformation();
		
		accountInformation.setUsername(username);
		accountInformation.setPassword(password);
		accountInformation.setNickname(nickname);
		accountInformation.seteMailAddress(eMailAddress);
		
		accountInformation.setActivationCode(activationCode);
		accountInformation.setActivationState("no");
		accountInformation.setAccountBalance(0.0);
		
		accountInformationDao.saveAccountInformation(accountInformation);
		
		Customer customer=new Customer();
		
		customer.setCustomerPhone(customerPhone);
		customer.setCustomerAccountInformation(accountInformation);
		
		customerDao.saveCustomer(customer);
		
		result.put("Result", "Success");
		return result;
	}
	
	public Map<String,Object> addCustomerReceivingInformation(String username,String customerName,String customerPhone,String customerAddress){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}if(customerName==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerName is null");
			return result;
		}if(customerPhone==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerPhone is null");
			return result;
		}if(customerAddress==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerAddress is null");
			return result;
		}
		
		Customer customer=customerDao.getCustomerByUsername(username);
		
		CustomerReceivingInformation customerReceivingInformation=new CustomerReceivingInformation();
		
		customerReceivingInformation.setCustomer(customer);
		customerReceivingInformation.setCustomerName(customerName);
		customerReceivingInformation.setCustomerPhone(customerPhone);
		customerReceivingInformation.setCustomerAddress(customerAddress);

		customerReceivingInformationDao.saveCustomerReceivingInformation(customerReceivingInformation);
		
		customer.getCustomerReceivingInformation().add(customerReceivingInformation);
		
		customerDao.saveOrUpdateCustomer(customer);
		
		result.put("Result", "Success");
		return result;
	}
	
	public Map<String,Object> deleteCustomerReceivingInformation(){
		
		
		return null;
	}
}
