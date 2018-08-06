package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Order;

@Repository("orderDao")
public class OrderDao {
	
	@Resource
	private BaseDao<Order> baseDao;

	public List<Order> getOrderByCarrierUsername(String username){
		List<Order> l = baseDao.find("select o from Order o where o.carrier.carrierAccountInformation.username=?", new Object[]{username},Order.class);
		return l;
	};
	
	public List<Order> getOrderByRestaurantUsername(String username){
		List<Order> l = baseDao.find("select o from Order o where o.restaurant.carrierAccountInformation.username=?", new Object[]{username},Order.class);
		return l;
	};
	
	public List<Order> getOrderByCarrierUsernameAndCustomerUsername(String carrierUsername,String customerUsername){
		List<Order> l = baseDao.find("select o from Order o where o.carrier.carrierAccountInformation.username=? "
				+ "and o.customerReceivingInformation.customer.carrierAccountInformation.username=?", 
				new Object[]{carrierUsername,customerUsername},Order.class);
		return l;
	};
	
	public List<Order> getOrderByRestaurantUsernameAndCustomerUsername(String restaurantUsername,String customerUsername){
		List<Order> l = baseDao.find("select o from Order o where o.restaurant.carrierAccountInformation.username=? "
				+ "and o.customerReceivingInformation.customer.carrierAccountInformation.username=?", 
				new Object[]{restaurantUsername,customerUsername},Order.class);
		return l;
	};
	
}
