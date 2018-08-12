package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.CommentFromRestaurantToCustomer;
import db.entity.Customer;
import db.entity.Restaurant;

@Repository("commentFromRestaurantToCustomerDao")
public class CommentFromRestaurantToCustomerDao {

	@Resource
	private BaseDao<CommentFromRestaurantToCustomer> baseDao;
	
	public void addCommentFromRestaurantToCustomer(Restaurant restaurant,Customer customer,String comment,Integer point) {
		CommentFromRestaurantToCustomer commentobj=new CommentFromRestaurantToCustomer();
		commentobj.setRestaurant(restaurant);
		commentobj.setCustomer(customer);
		commentobj.setComment(comment);
		commentobj.setPoint(point);
		baseDao.saveOrUpdate(commentobj);
	}
	
}
