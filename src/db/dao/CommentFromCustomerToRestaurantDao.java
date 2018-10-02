package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.CommentFromCustomerToRestaurant;
import db.entity.Customer;
import db.entity.Restaurant;

@Repository("commentFromCustomerToRestaurantDao")
public class CommentFromCustomerToRestaurantDao {

	@Resource
	private BaseDao<CommentFromCustomerToRestaurant> baseDao;
	
	public void addCommentFromCustomerToRestaurant(Customer customer,Restaurant restaurant,String comment,Integer point) {
		CommentFromCustomerToRestaurant commentobj=new CommentFromCustomerToRestaurant();
		commentobj.setRestaurant(restaurant);
		commentobj.setCustomer(customer);
		commentobj.setComment(comment);
		commentobj.setPoint(point);
		baseDao.saveOrUpdate(commentobj);
	}
	
	public List<CommentFromCustomerToRestaurant> getCommentFromCustomerToRestaurantByRestaurantId(Long id){
		return baseDao.find("select c from CommentFromCustomerToRestaurant c where c.restaurant.restaurantID=?0", new Object[] {id}, CommentFromCustomerToRestaurant.class);
	}
	
	public CommentFromCustomerToRestaurant getCommentFromCustomerToRestaurantByRestaurantIdAndCustomerId(Long restaurantId,Long customerId){
		return baseDao.get("select c from CommentFromCustomerToRestaurant c where c.restaurant.restaurantID=?0 and c.customer.customerID=?1"
				, new Object[] {restaurantId,customerId}, CommentFromCustomerToRestaurant.class);
	}
	
}
