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
	
	public Order getOderById(Long orderId) {
		return baseDao.get(Order.class, orderId);
	}
	
	public List<Order> getOrderByCustomerId(Long customerId) {
		return baseDao.find("select o from Order o where o.customerReceivingInformation.customer.customerID=?0", new Object[]{customerId},Order.class);
		
		
	}

	public List<Order> getOrderByCarrierUsername(String username){
		List<Order> l = baseDao.find("select o from Order o where o.carrier.carrierAccountInformation.username=?0", new Object[]{username},Order.class);
		return l;
	};
	
	public List<Order> getOrderByRestaurantUsername(String username){
		List<Order> l = baseDao.find("select o from Order o where o.restaurant.restaurantAccountInformation.username=?0", new Object[]{username},Order.class);
		return l;
	};
	
	public List<Order> getOrderByCarrierUsernameAndCustomerUsername(Long carrierId,Long customerId){
		List<Order> l = baseDao.find("select o from Order o where o.carrier.carrierID=?0 "
				+ "and o.customerReceivingInformation.customer.customerID=?1", 
				new Object[]{carrierId,customerId},Order.class);
		return l;
	};
	
	public List<Order> getOrderByRestaurantIdAndCustomerId(Long restaurantId,Long customerId){
		List<Order> l = baseDao.find("select o from Order o where o.restaurant.restaurantID=?0"
				+ "and o.customerReceivingInformation.customer.customerID=?1", 
				new Object[]{restaurantId,customerId},Order.class);
		return l;
	};
	
	public List<Order> getOrderByState(String state,Long start,Long end){
		List<Order> l = baseDao.find("select o from Order o where o.orderState=?0 and o.orderID>=?1 and o.orderID<=?2", 
				new Object[]{state,start,end},Order.class);
		return l;
	}
	
}
