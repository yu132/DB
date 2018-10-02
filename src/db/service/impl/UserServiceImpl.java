package db.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import db.dao.AccountInformationDao;
import db.dao.CarrierDao;
import db.dao.CustomerDao;
import db.dao.RestaurantDao;
import db.entity.AccountInformation;
import db.entity.Carrier;
import db.entity.Customer;
import db.entity.Restaurant;
import db.service.UserService;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private	AccountInformationDao accountInformationDao;
	
	@Resource
	private RestaurantDao restaurantDao;
	
	@Resource
	private CustomerDao customerDao;
	
	@Resource
	private CarrierDao carrierDao;

	/**
	 * 检查用户是否已经激活，根据用户名查找
	 */
	@Override
	public boolean checkAccountActivationStateByUsername(String username) {
		AccountInformation accountInformation=accountInformationDao.getAccountInformationByUsername(username);
		return checkAccountActivationStateByAccountInformation(accountInformation);
	}
	
	/**
	 * 检查用户是否已经激活，根据账号信息查找
	 */
	@Override
	public boolean checkAccountActivationStateByAccountInformation(AccountInformation accountInformation) {
		if(accountInformation==null)
			return false;
		if(accountInformation.getActivationState().toLowerCase().equals("yes"))
			return true;
		return false;
	}
	
	/**
	 * 检查用户知否注销或是没有登录（没用到）
	 */
	@Override
	public boolean checkUsernameExist(String username) {
		AccountInformation accountInformation=accountInformationDao.getAccountInformationByUsername(username);
		return accountInformation!=null;
	}
	
	/**
	 * 激活账号
	 */
	@Override
	public Map<String,Object> activateAccount(String username,String activationCode){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;	
		}if(activationCode==null) {
			result.put("Result", "Error");
			result.put("Reason", "ActivationCode is null");
			return result;	
		}
		
		AccountInformation accountInformation=accountInformationDao.getAccountInformationByUsername(username);
		
		if(!accountInformation.getActivationCode().equals(activationCode)) {
			result.put("Result", "Error");
			result.put("Reason", "ActivationCode is incorrect");
			return result;
		}
		
		accountInformation.setActivationState("yes");
		
		accountInformationDao.saveOrUpdateAccountInformation(accountInformation);
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 登录
	 */
	@Override
	public Map<String, Object> login(String username, String password,String kind) {
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;	
		}if(password==null) {
			result.put("Result", "Error");
			result.put("Reason", "Password is null");
			return result;	
		}if(kind==null) {
			result.put("Result", "Error");
			result.put("Reason", "Kind is null");
			return result;	
		}
		
		AccountInformation accountInformation;
		
		switch(kind) {
		case "Carrier":
			Carrier carrier=carrierDao.getCarrierByUsername(username);
			
			if(carrier==null) {
				result.put("Result", "Error");
				result.put("Reason", "User not exist");
				return result;	
			}
			
			accountInformation=carrier.getCarrierAccountInformation();
			
			result.put("Account", carrier);
			
			break;
		case "Customer":
			Customer customer=customerDao.getCustomerByUsername(username);
			
			if(customer==null) {
				result.put("Result", "Error");
				result.put("Reason", "User not exist");
				return result;	
			}
			
			accountInformation=customer.getCustomerAccountInformation();
			
			result.put("Account", customer);
			
			break;
		case "Restaurant":
			Restaurant restaurant=restaurantDao.getRestaurantByUsername(username);
			
			if(restaurant==null) {
				result.put("Result", "Error");
				result.put("Reason", "User not exist");
				return result;	
			}
			
			accountInformation=restaurant.getRestaurantAccountInformation();
			
			result.put("Account", restaurant);
			
			break;
		default:
			result.put("Result", "Error");
			result.put("Reason", "Kind is incorect");
			return result;	
		}
		
		if(!accountInformation.getPassword().equals(password)) {
			result.put("Result", "Error");
			result.put("Reason", "Password is not incorrect");
			result.remove("Account");
			return result;	
		}
		
		result.put("Result", "Success");
		return result;
	}

	/**
	 * 查询余额（盈利）
	 */
	@Override
	public Map<String, Object> getMoney(String username) {
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}
		
		AccountInformation accountInformation=accountInformationDao.getAccountInformationByUsername(username);
		
		if(!checkAccountActivationStateByAccountInformation(accountInformation)) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		result.put("Result", "Success");
		result.put("Money", accountInformation.getAccountBalance());
		return result;
	}

	
	/* (non-Javadoc)
	 * @see db.service.impl.UserService#depositMoney()
	 */
	@Override
	public Map<String,Object> depositMoney(){
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.UserService#WithdrawMoney()
	 */
	@Override
	public Map<String,Object> WithdrawMoney(){
		
		return null;
	}
}
