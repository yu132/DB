package db.action.carrierAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.CarrierService;

public class Register extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
	@Resource
	private CarrierService carrierService;
	
	private String name;
	
	private String phone;
	
	private String username;
	
	private String password;
	
	private String nickname;
	
	private String eMailAddress;
	
	@Override
	public String execute() throws Exception {
		Map<String,Object> result=carrierService.register(name, phone, username, password, nickname, eMailAddress);
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
