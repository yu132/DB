package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Carrier;
import db.entity.CommentFromCustomerToCarrier;
import db.entity.Customer;

@Repository("commentFromCustomerToCarrierDao")
public class CommentFromCustomerToCarrierDao {

	@Resource
	private BaseDao<CommentFromCustomerToCarrier> baseDao;
	
	public void addCommentFromCustomerToCarrier(Customer customer,Carrier carrier,String comment,Integer point) {
		CommentFromCustomerToCarrier commentobj=new CommentFromCustomerToCarrier();
		commentobj.setCarrier(carrier);
		commentobj.setCustomer(customer);
		commentobj.setComment(comment);
		commentobj.setPoint(point);
		baseDao.saveOrUpdate(commentobj);
	}
	
}
