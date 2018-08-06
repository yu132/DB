package db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import db.dao.AccountInformationDao;
import db.dao.CarrierDao;
import db.dao.CommentFromCarrierToCustomerDao;
import db.dao.CustomerDao;
import db.dao.OrderDao;
import db.entity.AccountInformation;
import db.entity.Carrier;
import db.entity.Customer;
import db.entity.Order;
import db.util.randomstr.RandomStr;
import db.util.sendEmail.EmailSender;

@Service("carrierService")
public class CarrierServiceImpl {
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private CommentFromCarrierToCustomerDao commentFromCarrierToCustomerDao;
	
	@Resource
	private CustomerDao customerDao;
	
	@Resource
	private CarrierDao carrierDao;
	
	@Resource
	private	AccountInformationDao accountInformationDao;
	
	@Resource
	private UserServiceImpl userServiceImpl;
	
	public Map<String,Object> getOrder(String username){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;	
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(username)) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		result.put("Result", "Success");
		result.put("ResultList", orderDao.getOrderByCarrierUsername(username));
		
		return result;
	};
	
	public Map<String,Object> giveCommentToCustomer(String username,String customerUsername,String comment,Integer point){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}if(customerUsername==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerUsername is null");
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
		List<Order> orderlist=orderDao.getOrderByCarrierUsernameAndCustomerUsername(username, customerUsername);
		
		if(orderlist.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to comment on this customer");
			return result;
		}
		
		Carrier carrier=carrierDao.getCarrierByUsername(username);
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is incorrect");
			return result;
		}
		
		Customer customer=customerDao.getCustomerByUsername(customerUsername);
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer's username is incorrect");
			return result;
		}
	
		commentFromCarrierToCustomerDao.addCommentFromCarrierToCustomer(carrier, customer, comment,point);
	
		result.put("Result", "Success");
		
		return result;
	};
	
	public Map<String,Object> register(String name,String phone,String username,String password,String nickname,String eMailAddress){
		
		Map<String,Object> result=new HashMap<>();
		
		if(name==null) {
			result.put("Result", "Error");
			result.put("Reason", "Name is null");
			return result;
		}if(phone==null) {
			result.put("Result", "Error");
			result.put("Reason", "Phone is null");
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
		
		Carrier carrier=new Carrier();
		
		carrier.setCarrierName(name);
		carrier.setCarrierState("rest");
		carrier.setCarrierPhone(phone);
		carrier.setCarrierAccountInformation(accountInformation);
		
		carrierDao.saveCarrier(carrier);
		
		if(!EmailSender.sendActivationEmail(username, activationCode, eMailAddress)){
			result.put("Result", "SuccessWithError");
			result.put("Reason", "Email send error");
			return result;
		}
		
		result.put("Result", "Success");
		return result;
	};
	
	public Map<String,Object> start(String username){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(username)) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Carrier carrier=carrierDao.getCarrierByUsername(username);
		
		carrier.setCarrierState("work");
		
		carrierDao.saveOrUpdateCarrier(carrier);
		
		result.put("Result", "Success");
		return result;
	};
	
	public Map<String,Object> stop(String username){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(username)) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Carrier carrier=carrierDao.getCarrierByUsername(username);
		
		carrier.setCarrierState("rest");
		
		carrierDao.saveOrUpdateCarrier(carrier);
		
		result.put("Result", "Success");
		return result;
	};
	
	public Map<String,Object> getAvailableOrder(String username){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}
		
		//需要算出两者距离，选出比较适合的单子来接，太远了就不适合了
		
		//其实是外卖小哥到饭店的距离加上饭店到客户的距离
		
		//现在先不做，挂一会先
		
		
		return result;
	}
	
	public Map<String,Object> getCustomerComment(String username,String customerUsername){
		
		
		return null;
	}
	
}
