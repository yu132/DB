package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Customer;

@Repository("customerDao")
public class CustomerDao {

	@Resource
	private BaseDao<Customer> baseDao;
	
	public Customer getCustomerByUsername(String username) {
		return baseDao.get("select c from Customer c where c.customerAccountInformation.username=?0", new Object[] {username}, Customer.class);
	}
	
	public void saveCustomer(Customer customer) {
		baseDao.save(customer);
	}
	
	public void saveOrUpdateCustomer(Customer customer) {
		baseDao.saveOrUpdate(customer);
	}
	
}
