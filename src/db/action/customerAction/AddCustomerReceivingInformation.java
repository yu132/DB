package db.action.customerAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Customer;
import db.service.CustomerService;

public class AddCustomerReceivingInformation extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;

	private HttpSession session;
	
	@Resource
	private CustomerService customerService;
	
	private String customerName;
	
	private String customerPhone;
	
	private String customerAddress;
	
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
		
		Map<String,Object> result=customerService.addCustomerReceivingInformation(customer, customerName, customerPhone, customerAddress);
		
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
}
