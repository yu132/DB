package db.service;

import java.util.Map;

import db.entity.AccountInformation;

public interface UserService {

	boolean checkAccountActivationStateByUsername(String username);

	boolean checkAccountActivationStateByAccountInformation(AccountInformation accountInformation);

	/**
	 * 
	 * @param username
	 * @return true 如果用户名存在； false 如果用户名不存在
	 */
	boolean checkUsernameExist(String username);

	Map<String, Object> activateAccount(String username, String activationCode);

	Map<String, Object> depositMoney();

	Map<String, Object> WithdrawMoney();
	
	Map<String, Object> login(String username,String password,String kind);
	
	Map<String, Object> getMoney(String username);
}