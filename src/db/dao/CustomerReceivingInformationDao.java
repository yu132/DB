package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Customer;
import db.entity.CustomerReceivingInformation;

@Repository("customerReceivingInformationDao")
public class CustomerReceivingInformationDao {

	@Resource
	private BaseDao<CustomerReceivingInformation> baseDao;
	
	public CustomerReceivingInformation getCustomerReceivingInformationById(Long id) {
		return baseDao.get(CustomerReceivingInformation.class, id);
	}
	
	public List<CustomerReceivingInformation> getCustomerReceivingInformationByCustomer(Customer customer) {
		return baseDao.find("select c from CustomerReceivingInformation c where c.customer.customerID=?0", new Object[] {customer.getCustomerID()}, CustomerReceivingInformation.class);
	}
	
	public void saveCustomerReceivingInformation(CustomerReceivingInformation customerReceivingInformation) {
		baseDao.save(customerReceivingInformation);
	}
	
	public void deleteCustomerReceivingInformation(CustomerReceivingInformation customerReceivingInformation) {
		baseDao.delete(customerReceivingInformation);
	}
	
}
