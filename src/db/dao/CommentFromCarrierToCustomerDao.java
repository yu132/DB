package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Carrier;
import db.entity.CommentFromCarrierToCustomer;
import db.entity.Customer;

@Repository("commentFromCarrierToCustomerDao")
public class CommentFromCarrierToCustomerDao {

	@Resource
	private BaseDao<CommentFromCarrierToCustomer> baseDao;
	
	public void addCommentFromCarrierToCustomer(Carrier carrier,Customer customer,String comment,Integer point) {
		CommentFromCarrierToCustomer commentobj=new CommentFromCarrierToCustomer();
		commentobj.setCarrier(carrier);
		commentobj.setCustomer(customer);
		commentobj.setComment(comment);
		commentobj.setPoint(point);
		baseDao.saveOrUpdate(commentobj);
	}
	
}
