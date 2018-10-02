package db.action.userAction;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Carrier;
import db.entity.Customer;
import db.entity.Restaurant;
import db.service.UserService;

public class GoHomePage extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;

	private HttpSession session;
	
	@Resource
	private UserService userService;
	
	@Override
	public String execute() throws Exception {
		Object account=session.getAttribute("CurrentUser");
		
		if(account==null) {
			request.setAttribute("Reason", "User not logged in");
			return "Error";
		}
		
		if(account instanceof Customer) {
			request.setAttribute("CurrentUser", account);
			return "Customer";
		}else if(account instanceof Carrier) {
			request.setAttribute("CurrentUser", account);
			return "Carrier";
		}else if(account instanceof Restaurant) {
			request.setAttribute("CurrentUser", account);
			return "Restaurant";
		}else {
			request.setAttribute("Reason", "User type is incorrect");
			return "Error";
		}
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session = request.getSession();
	}
}
