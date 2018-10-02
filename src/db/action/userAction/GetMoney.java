package db.action.userAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.AccountInformation;
import db.entity.Carrier;
import db.entity.Restaurant;
import db.service.UserService;

public class GetMoney extends ActionSupport implements ServletRequestAware{

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
		
		AccountInformation ai;
		
		if(account instanceof Restaurant) {
			ai=((Restaurant)account).getRestaurantAccountInformation();
		}else if(account instanceof Carrier) {
			ai=((Carrier)account).getCarrierAccountInformation();
		}else {
			request.setAttribute("Reason", "User type is incorrect");
			return "Error";
		}
		
		Map<String,Object> result=userService.getMoney(ai.getUsername());
		
		String state=(String) result.get("Result");
		
		if(state.equals("Success")) {
			request.setAttribute("Money", result.get("Money"));
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
}
