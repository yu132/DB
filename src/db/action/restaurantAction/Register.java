package db.action.restaurantAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.RestaurantService;

public class Register extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
	@Resource
	private RestaurantService restaurantService;
	
	private String restaurantName;
	
	private String restaurantAddress;
	
	private String restaurantPhone;
	
	private String username;
	
	private String password;
	
	private String nickname;
	
	private String eMailAddress;
	
	@Override
	public String execute() throws Exception {
		
		Map<String,Object> result=restaurantService.register(restaurantName, restaurantAddress, restaurantPhone, username, password, nickname, eMailAddress);
		
		String state=(String) result.get("Result");
		
	if(state.equals("Success")) {
			
		}else if(state.equals("SuccessWithError")){
			request.setAttribute("Reason", result.get("Reason"));
		}else if(state.equals("Error")){
			request.setAttribute("Reason", result.get("Reason"));
		}
		
		return state;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String geteMailAddress() {
		return eMailAddress;
	}

	public void seteMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}
	
}
