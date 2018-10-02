package db.action.restaurantAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Restaurant;
import db.service.RestaurantService;

public class ChangeInformation extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
	private HttpSession session;
	
	@Resource
	private RestaurantService restaurantService;
	
	private String restaurantName;
	
	private String restaurantAddress;
	
	private String restaurantPhone;
	
	@Override
	public String execute() throws Exception {
		Object account=session.getAttribute("CurrentUser");
		
		if(account==null) {
			request.setAttribute("Reason", "User not logged in");
			return "Error";
		}
		
		if(!(account instanceof Restaurant)) {
			request.setAttribute("Reason", "User type is incorrect");
		}
		
		Restaurant restaurant=(Restaurant)account;
		
		Map<String,Object> result=restaurantService.changeInformation(restaurant, restaurantName, restaurantAddress, restaurantPhone);
		
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

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantAddress() {
		return restaurantAddress;
	}

	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}

	public String getRestaurantPhone() {
		return restaurantPhone;
	}

	public void setRestaurantPhone(String restaurantPhone) {
		this.restaurantPhone = restaurantPhone;
	}
	
}
