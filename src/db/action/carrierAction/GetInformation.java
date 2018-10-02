package db.action.carrierAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Carrier;
import db.service.CarrierService;

public class GetInformation extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
	private HttpSession session;
	
	@Resource
	private CarrierService carrierService;
	
	@Override
	public String execute() throws Exception {
		Object account=session.getAttribute("CurrentUser");
		
		if(account==null) {
			request.setAttribute("Reason", "User not logged in");
			return "Error";
		}
		
		if(!(account instanceof Carrier)) {
			request.setAttribute("Reason", "User type is incorrect");
		}
		
		Carrier carrier=(Carrier)account;
		
		Map<String,Object> result=carrierService.getInformation(carrier);
		
		String state=(String) result.get("Result");
		
		if(state.equals("Success")) {
			request.setAttribute("Carrier", result.get("Carrier"));
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
