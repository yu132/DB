package db.dao;

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
	
}
