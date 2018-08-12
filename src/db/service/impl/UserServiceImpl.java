package db.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import db.dao.AccountInformationDao;
import db.entity.AccountInformation;
import db.service.UserService;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private	AccountInformationDao accountInformationDao;

	/* (non-Javadoc)
	 * @see db.service.impl.UserService#checkAccountActivationStateByUsername(java.lang.String)
	 */
	@Override
	public boolean checkAccountActivationStateByUsername(String username) {
		AccountInformation accountInformation=accountInformationDao.getAccountInformationByUsername(username);
		return checkAccountActivationStateByAccountInformation(accountInformation);
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.UserService#checkAccountActivationStateByAccountInformation(db.entity.AccountInformation)
	 */
	@Override
	public boolean checkAccountActivationStateByAccountInformation(AccountInformation accountInformation) {
		if(accountInformation==null)
			return false;
		if(accountInformation.getActivationState().toLowerCase().equals("yes"))
			return true;
		return false;
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.UserService#checkUsernameExist(java.lang.String)
	 */
	@Override
	public boolean checkUsernameExist(String username) {
		AccountInformation accountInformation=accountInformationDao.getAccountInformationByUsername(username);
		return accountInformation!=null;
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.UserService#activateAccount(java.lang.String, java.lang.String)
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
