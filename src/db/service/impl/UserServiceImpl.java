package db.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import db.dao.AccountInformationDao;
import db.entity.AccountInformation;

@Service("userService")
public class UserServiceImpl {
	
	@Resource
	private	AccountInformationDao accountInformationDao;

	public boolean checkAccountActivationStateByUsername(String username) {
		AccountInformation accountInformation=accountInformationDao.getAccountInformationByUsername(username);
		if(accountInformation.getActivationState().toLowerCase().equals("yes"))
			return true;
		return false;
	}
	
	/**
	 * 
	 * @param username
	 * @return true 如果用户名存在； false 如果用户名不存在
	 */
	public boolean checkUsernameExist(String username) {
		AccountInformation accountInformation=accountInformationDao.getAccountInformationByUsername(username);
		return accountInformation!=null;
	}
	
}
