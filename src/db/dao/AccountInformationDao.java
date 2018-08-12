package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.AccountInformation;

@Repository("accountInformationDao")
public class AccountInformationDao {

	@Resource
	private BaseDao<AccountInformation> baseDao;
	
	public void saveAccountInformation(AccountInformation accountInformation) {
		baseDao.save(accountInformation);
	}
	
	public void saveOrUpdateAccountInformation(AccountInformation accountInformation) {
		baseDao.saveOrUpdate(accountInformation);
	}
	
	public AccountInformation getAccountInformationByUsername(String username) {
		return baseDao.get("select a from AccountInformation a where a.username=?0", new Object[] {username}, AccountInformation.class);
	}
	
}
