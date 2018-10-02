package db.action.customerAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Customer;
import db.entity.Order;
import db.service.CustomerService;

public class ToPay extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;

	private HttpSession session;
	
	@Resource
	private CustomerService customerService;
	
	private Long orderId;
	
	@Override
	public String execute() throws Exception {
		Object account=session.getAttribute("CurrentUser");
		
		if(account==null) {
			request.setAttribute("Reason", "User not logged in");
			return "Error";
		}
		
		if(!(account instanceof Customer)) {
			request.setAttribute("Reason", "User type is incorrect");
			return "Error";
		}
		
		Customer customer=(Customer)account;
		
		Map<String, Object> result=customerService.getOrderDetail(customer, orderId);
		
		String state=(String) result.get("Result");
		
		if(state.equals("Success")) {
			request.setAttribute("OrderId", orderId);
			request.setAttribute("MoneyNeedPay", ((Order)result.get("Order")).getOrderPrice());
		}else if(state.equals("Error")){
			request.setAttribute("Reason", result.get("Reason"));
		}
		
		return "Success";
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session = request.getSession();
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
