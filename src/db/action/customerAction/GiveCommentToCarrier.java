package db.action.customerAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Customer;
import db.service.CustomerService;

public class GiveCommentToCarrier extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;

	private HttpSession session;
	
	@Resource
	private CustomerService customerService;
	
	private Long carrierId;
	
	private String comment;
	
	private Integer point;
	
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
		
		Map<String,Object> result=customerService.giveCommentToCarrier(customer, carrierId, comment, point);
		
		String state=(String) result.get("Result");
		
		if(state.equals("Success")) {
		}else if(state.equals("Error")){
			request.setAttribute("Reason", result.get("Reason"));
		}
		
		return state;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session = request.getSession();
	}


	public Long getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(Long carrierId) {
		this.carrierId = carrierId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
	
}

