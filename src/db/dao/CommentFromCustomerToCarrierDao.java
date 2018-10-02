package db.dao;

import java.util.List;

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
	
	public List<CommentFromCustomerToCarrier> getCommentFromCustomerToCarrierByCarrierId(Long id){
		return baseDao.find("select c from CommentFromCustomerToCarrier c where c.carrier.carrierID=?0", new Object[] {id}, CommentFromCustomerToCarrier.class);
	}
	
	public CommentFromCustomerToCarrier getCommentFromCustomerToCarrierByCarrierIdAndCustomerId(Long carrierId,Long customerId){
		return baseDao.get("select c from CommentFromCustomerToCarrier c where c.carrier.carrierID=?0 and c.customer.customerID=?1"
				, new Object[] {carrierId,customerId}, CommentFromCustomerToCarrier.class);
	}
	
}
