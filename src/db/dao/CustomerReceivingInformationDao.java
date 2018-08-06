package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.CustomerReceivingInformation;

@Repository("customerReceivingInformationDao")
public class CustomerReceivingInformationDao {

	@Resource
	private BaseDao<CustomerReceivingInformation> baseDao;
	
	public CustomerReceivingInformation getCustomerReceivingInformationById(Long id) {
		return baseDao.get(CustomerReceivingInformation.class, id);
	}
	
	public void saveCustomerReceivingInformation(CustomerReceivingInformation customerReceivingInformation) {
		baseDao.save(customerReceivingInformation);
	}
	
}
