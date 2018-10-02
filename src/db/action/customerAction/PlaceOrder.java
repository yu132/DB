package db.action.customerAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.action.customerAction.ConfirmOrder.OrderSave;
import db.entity.Customer;
import db.service.CustomerService;

public class PlaceOrder extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;

	private HttpSession session;
	
	@Resource
	private CustomerService customerService;
	
	private Integer orderIndex;
	
	private Long customerReceivingInformationId;
	
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
		
		Object mapx=session.getAttribute("OrderIndexMap");
		
		Map<Integer,OrderSave> map;
		
		if(mapx==null) {
			request.setAttribute("Reason", "Order not exists or time out");
			return "Error";
		}else {
			map=(Map<Integer, OrderSave>) mapx;
		}
		
		OrderSave os=map.get(orderIndex);
		
		if(os==null) {
			request.setAttribute("Reason", "Order not exists");
			return "Error";
		}
		
		Map<String, Object> result=customerService.placeOrder(customer, System.currentTimeMillis(), null, os.getRestaurantId(), customerReceivingInformationId, os.getMenus(),os.getPrice());		
		
		String state=(String) result.get("Result");
		
		if(state.equals("Success")) {
			request.setAttribute("MoneyNeedPay", os.getPrice()+ (Double)result.get("CarrierMoney"));
			request.setAttribute("CarrierMoney", result.get("CarrierMoney"));
			request.setAttribute("OrderId", result.get("OrderId"));
		}else if(state.equals("Error")){
			request.setAttribute("Reason", result.get("Reason"));
		}
		
		return state;
	}
	
	public static class MenuIdAndNumber{
		private Long menuId;
		private String menuName;
		private Integer menuNumber;
		public Long getMenuId() {
			return menuId;
		}
		public void setMenuId(Long menuId) {
			this.menuId = menuId;
		}
		public Integer getMenuNumber() {
			return menuNumber;
		}
		public void setMenuNumber(Integer menuNumber) {
			this.menuNumber = menuNumber;
		}
		public String getMenuName() {
			return menuName;
		}
		public void setMenuName(String menuName) {
			this.menuName = menuName;
		}
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session = request.getSession();
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Long getCustomerReceivingInformationId() {
		return customerReceivingInformationId;
	}

	public void setCustomerReceivingInformationId(Long customerReceivingInformationId) {
		this.customerReceivingInformationId = customerReceivingInformationId;
	}
}
