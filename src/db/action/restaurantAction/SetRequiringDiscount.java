package db.action.restaurantAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Restaurant;
import db.service.RestaurantService;

public class SetRequiringDiscount extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
	private HttpSession session;
	
	@Resource
	private RestaurantService restaurantService;
	
	private Double requiringMoney;
	
	private Double discountMoney;
	
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
		
		Map<String,Object> result=restaurantService.setRequiringDiscount(restaurant, requiringMoney, discountMoney);
		
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

	public Double getRequiringMoney() {
		return requiringMoney;
	}

	public void setRequiringMoney(Double requiringMoney) {
		this.requiringMoney = requiringMoney;
	}

	public Double getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(Double discountMoney) {
		this.discountMoney = discountMoney;
	}
	
}
