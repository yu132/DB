package db.action.customerAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Customer;
import db.entity.Menu;
import db.service.CustomerService;

public class GetRestaurantMenu extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;

	private HttpSession session;
	
	@Resource
	private CustomerService customerService;
	
	private Long id;
	
	@SuppressWarnings("unchecked")
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
		
		Map<String,Object> result=customerService.getRestaurantInformationById(customer, id);
		
		String state=(String) result.get("Result");
		
		if(state.equals("Success")) {
			request.setAttribute("RestaurantInformation", result.get("RestaurantInformation"));
			request.setAttribute("RestaurantMenu", result.get("RestaurantMenu"));
			
			Object o=session.getAttribute("IdPriceMap");
			Map<Long,Double> idPriceMap;
					
			if(o==null) {
				idPriceMap=new HashMap<>();
				session.setAttribute("IdPriceMap",idPriceMap);
			}else {
				idPriceMap=(Map<Long,Double>)o;
			}
			
			List<Menu> mlist=(List<Menu>) result.get("RestaurantMenu");
			
			for(Menu m:mlist) {
				idPriceMap.put(m.getMenuID(), m.getMenuPrice()*m.getMenuDiscount());
			}
			
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
