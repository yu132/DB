package db.dao;

import java.util.List;

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
	
	public List<CommentFromCarrierToCustomer> getCommentFromCarrierToCustomerByCustomerId(Long id){
		return baseDao.find("select c from CommentFromCarrierToCustomer c where c.customer.customerID=?0", new Object[] {id}, CommentFromCarrierToCustomer.class);
	}
	
	public CommentFromCarrierToCustomer getCommentFromCarrierToCustomerByCarrierIdAndCustomerId(Long carrierId,Long customerId){
		return baseDao.get("select c from CommentFromCarrierToCustomer c where c.carrier.carrierID=?0 and c.customer.customerID=?1", new Object[] {carrierId,customerId}, CommentFromCarrierToCustomer.class);
	}
	
}
