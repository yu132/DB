package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Order;

@Repository("orderDao")
public class OrderDao {
	
	@Resource
	private BaseDao<Order> baseDao;
	
	public void saveOrder(Order order) {
		baseDao.save(order);
	}
	
	public void saveOrUpdateOrder(Order order) {
		baseDao.saveOrUpdate(order);
	}

	public List<Order> getOrderByCarrierUsername(String username){
		List<Order> l = baseDao.find("select o from Order o where o.carrier.carrierAccountInformation.username=?0", new Object[]{username},Order.class);
		return l;
	};
	
	public List<Order> getOrderByRestaurantUsername(String username){
		List<Order> l = baseDao.find("select o from Order o where o.restaurant.carrierAccountInformation.username=?0", new Object[]{username},Order.class);
		return l;
	};
	
	public List<Order> getOrderByCarrierUsernameAndCustomerUsername(String carrierUsername,String customerUsername){
		List<Order> l = baseDao.find("select o from Order o where o.carrier.carrierAccountInformation.username=?0 "
				+ "and o.customerReceivingInformation.customer.carrierAccountInformation.username=?1", 
				new Object[]{carrierUsername,customerUsername},Order.class);
		return l;
	};
	
	public List<Order> getOrderByRestaurantUsernameAndCustomerUsername(String restaurantUsername,String customerUsername){
		List<Order> l = baseDao.find("select o from Order o where o.restaurant.restaurantAccountInformation.username=? "
				+ "and o.customerReceivingInformation.customer.carrierAccountInformation.username=?", 
				new Object[]{restaurantUsername,customerUsername},Order.class);
		return l;
	};
	
}
