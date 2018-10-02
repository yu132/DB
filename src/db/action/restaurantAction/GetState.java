package db.action.restaurantAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Restaurant;
import db.service.RestaurantService;

public class GetState extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
	private HttpSession session;
	
	@Resource
	private RestaurantService restaurantService;
	
	@Override
	public String execute() throws Exception {
		Object account=session.getAttribute("CurrentUser");
		
		if(account==null) {
			request.setAttribute("Reason", "User not logged in");
			return "Error";
		}
		
		if(!(account instanceof Restaurant)) {
			request.setAttribute("Reason", "User type is incorrect");
			return "Error";
		}
		
		Restaurant restaurant=(Restaurant)account;
		
		Map<String,Object> result=restaurantService.getState(restaurant);
		
		String state=(String) result.get("Result");
		
		if(state.equals("Success")) {
			request.setAttribute("State", result.get("State").equals("open")?"营业":"关闭");
			request.setAttribute("CurrentUser", account);
			
			String stateOtherChinese=result.get("State").equals("open")?"关闭":"营业";
			
			request.setAttribute("StateOtherChinese", stateOtherChinese);
			
			String changeStateAction=result.get("State").equals("open")?"close.action":"open.action";
			
			request.setAttribute("ChangeStateAction", changeStateAction);
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
