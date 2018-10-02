package db.action.customerAction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.action.customerAction.PlaceOrder.MenuIdAndNumber;
import db.entity.Customer;
import db.entity.CustomerReceivingInformation;
import db.service.CustomerService;

public class ConfirmOrder extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;

	private HttpSession session;
	
	@Resource
	private CustomerService customerService;
	
	private List<MenuIdAndNumber> menuList;
	
	private Long restaurantId;
	
	@SuppressWarnings({ "unchecked"})
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
		
		for(Iterator<MenuIdAndNumber> it=menuList.iterator();it.hasNext();) {
			MenuIdAndNumber nnan=it.next();
			if(nnan.getMenuNumber()<=0) {
				it.remove();
			}
		}
		
		if(menuList.size()==0) {
			request.setAttribute("Reason", "Order is empty");
			return "Error";
		}
		
		Map<String,Object> result=customerService.checkMenu(customer, menuList, restaurantId);
		
		String state=(String) result.get("Result");
		
		if(state.equals("Success")) {
		}else if(state.equals("Error")){
			request.setAttribute("Reason", result.get("Reason"));
			return "Error";
		}
		
		OrderSave os=new OrderSave();
		
		os.setMenus(menuList);
		
		os.setRestaurantId(restaurantId);
		
		int index;
		
		Object o = session.getAttribute("OrderIndex");
		
		if(o==null) {
			index=0;
			session.setAttribute("OrderIndex",0);
		}else {
			index=(int)o+1;
			session.setAttribute("OrderIndex",index);
		}
		
		request.setAttribute("OrderIndex", index);
		
		Object ipm=session.getAttribute("IdPriceMap");
		
		Map<Long,Double> idPriceMap;
		
		if(ipm==null) {
			request.setAttribute("Reason", "Menu price is not in session");
			return "Error";
		}else {
			idPriceMap=(Map<Long, Double>) ipm;
		}
		
		double totalMoney=0;
		
		for(int i=0;i<menuList.size();i++) {
			totalMoney+=idPriceMap.get(menuList.get(i).getMenuId())*menuList.get(i).getMenuNumber();
		}
		
		totalMoney=(double)(int)(totalMoney*100)/100.0;
		
		request.setAttribute("TotalMoney", totalMoney);
		
		os.setPrice(totalMoney);
		
		Object mapx=session.getAttribute("OrderIndexMap");
		
		Map<Integer,OrderSave> map;
		
		if(mapx==null) {
			map=new HashMap<>();
			session.setAttribute("OrderIndexMap", map);
		}else {
			map=(Map<Integer, OrderSave>) mapx;
		}
		
		map.put(index, os);
		
		Map<String,Object> result2=customerService.getRestaurantInformationByIdWithoutMenu(customer, restaurantId);
		
		request.setAttribute("RestaurantInformation", result2.get("RestaurantInformation"));
	
		return "Success";
	}
	
	public static class OrderSave{
		private Long restaurantId;
		private List<MenuIdAndNumber> menus;
		private Double price;
		private CustomerReceivingInformation customerReceivingInformation;
		
		public Long getRestaurantId() {
			return restaurantId;
		}
		public void setRestaurantId(Long restaurantId) {
			this.restaurantId = restaurantId;
		}
		public List<MenuIdAndNumber> getMenus() {
			return menus;
		}
		public void setMenus(List<MenuIdAndNumber> menus) {
			this.menus = menus;
		}
		public Double getPrice() {
			return price;
		}
		public void setPrice(Double price) {
			this.price = price;
		}
		public CustomerReceivingInformation getCustomerReceivingInformation() {
			return customerReceivingInformation;
		}
		public void setCustomerReceivingInformation(CustomerReceivingInformation customerReceivingInformation) {
			this.customerReceivingInformation = customerReceivingInformation;
		}
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session = request.getSession();
	}

	public List<MenuIdAndNumber> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuIdAndNumber> menuList) {
		this.menuList = menuList;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	
}