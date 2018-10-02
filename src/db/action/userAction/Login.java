package db.action.userAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.UserService;

public class Login extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;

	private HttpSession session;
	
	private String username;
	
	private String password;
	
	private String kind;
	
	@Resource
	private UserService userService;
	
	@Override
	public String execute() throws Exception {
		Map<String,Object> result=userService.login(username, password,kind);
		
		String state=(String) result.get("Result");
		
		if(state.equals("Success")) {
			session.setAttribute("CurrentUser", result.get("Account"));
			request.setAttribute("CurrentUser", result.get("Account"));
			return kind;
		}else if(state.equals("Error")){
			request.setAttribute("Reason", result.get("Reason"));
			return "Error";
		}else 
			return "Error";
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

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session = request.getSession();
	}
}
