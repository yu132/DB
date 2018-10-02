package db.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import db.dao.AccountInformationDao;
import db.dao.CommentFromRestaurantToCustomerDao;
import db.dao.CustomerDao;
import db.dao.MenuDao;
import db.dao.OrderDao;
import db.dao.RequiringDiscountDao;
import db.dao.RestaurantDao;
import db.dao.VoucherActivityDao;
import db.entity.AccountInformation;
import db.entity.Carrier;
import db.entity.Menu;
import db.entity.MenuOrder;
import db.entity.Order;
import db.entity.RequiringDiscount;
import db.entity.Restaurant;
import db.entity.VoucherActivity;
import db.service.RestaurantService;
import db.service.UserService;
import db.util.randomstr.RandomStr;
import db.util.sendEmail.EmailSender;

@Transactional
@Service("restaurantService")
public class RestaurantServiceImpl implements RestaurantService {
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private	AccountInformationDao accountInformationDao;
	
	@Resource
	private RestaurantDao restaurantDao;
	
	@Resource
	private CustomerDao customerDao;
	
	@Resource
	private MenuDao menuDao;
	
	@Resource
	private VoucherActivityDao voucherActivityDao;
	
	@Resource
	private RequiringDiscountDao requiringDiscountDao;
	
	@Resource
	private CommentFromRestaurantToCustomerDao commentFromRestaurantToCustomerDao;
	
	@Resource
	private UserService userServiceImpl;

	/**
	 * 添加一个菜单
	 */
	@Override
	public Map<String,Object> addMenu(Restaurant restaurant,String menuName,Double menuPrice,Double menuDiscount){
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}if(menuName==null) {
			result.put("Result", "Error");
			result.put("Reason", "MenuName is null");
			return result;
		}if(menuPrice==null) {
			result.put("Result", "Error");
			result.put("Reason", "MenuPrice is null");
			return result;
		}if(menuDiscount==null) {
			result.put("Result", "Error");
			result.put("Reason", "MenuDiscount is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Menu menu=new Menu();
		
		menu.setMenuName(menuName);
		menu.setMenuPrice(menuPrice);
		menu.setMenuDiscount(menuDiscount);
		menu.setRestaurant(restaurant);
		
		menuDao.saveMenu(menu);
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 删除一个菜单
	 */
	@Override
	public Map<String,Object> deleteMenu(Restaurant restaurant,Long menuID){
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}if(menuID==null) {
			result.put("Result", "Error");
			result.put("Reason", "MenuID is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Menu menu=menuDao.getMenuById(menuID);
		
		if(menu==null) {
			result.put("Result", "Error");
			result.put("Reason", "MenuID is incorrect");
			return result;
		}
		
		if(!menu.getRestaurant().equals(restaurant)) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to delete this menu");
		}
		
		menuDao.deleteMenu(menu);
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 添加一个代金券（没有用到，因为没有做代金卷功能）
	 */
	@Override
	public Map<String,Object> addVoucherActicity(Restaurant restaurant,Double needPay,Double discountMoney,Double needToUse,Long validTime){
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}if(needPay==null) {
			result.put("Result", "Error");
			result.put("Reason", "NeedPay is null");
			return result;
		}if(discountMoney==null) {
			result.put("Result", "Error");
			result.put("Reason", "DiscountMoney is null");
			return result;
		}if(needToUse==null) {
			result.put("Result", "Error");
			result.put("Reason", "NeedToUse is null");
			return result;
		}if(validTime==null) {
			result.put("Result", "Error");
			result.put("Reason", "ValidTime is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		VoucherActivity voucherActivity=new VoucherActivity();
		
		voucherActivity.setRestaurant(restaurant);
		voucherActivity.setNeedPay(needPay);
		voucherActivity.setDiscountMoney(discountMoney);
		voucherActivity.setNeedToUse(needToUse);
		voucherActivity.setValidTime(validTime);
		
		voucherActivityDao.saveVoucherActivity(voucherActivity);
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 删除一个代金券（没有用到，因为没有做代金卷功能）
	 */
	@Override
	public Map<String,Object> deleteVoucherActicity(Restaurant restaurant,Long voucherActicityID){
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}if(voucherActicityID==null) {
			result.put("Result", "Error");
			result.put("Reason", "VoucherActicityID is null");
			return result;
		}
		
		VoucherActivity voucherActivity=voucherActivityDao.getVoucherActivityById(voucherActicityID);
		
		if(voucherActivity==null) {
			result.put("Result", "Error");
			result.put("Reason", "VoucherActicityID is incorrect");
			return result;
		}
		
		if(!voucherActivity.getRestaurant().equals(restaurant)) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to delete this VoucherActivity");
		}
		
		voucherActivityDao.deleteVoucherActivity(voucherActivity);
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 将餐馆状态切换到营业
	 */
	@Override
	public Map<String,Object> open(Restaurant restaurant){
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}

		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		restaurant=restaurantDao.getRestaurantById(restaurant.getRestaurantID());
		
		restaurant.setRestaurantState("open");
		
		restaurantDao.saveOrUpdateRestaurant(restaurant);
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 将餐馆状态切换到关闭
	 */
	@Override
	public Map<String,Object> close(Restaurant restaurant){
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		restaurant=restaurantDao.getRestaurantById(restaurant.getRestaurantID());
		
		restaurant.setRestaurantState("close");
		
		restaurantDao.saveOrUpdateRestaurant(restaurant);
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 获取餐馆收到的所有订单
	 */
	@Override
	public Map<String,Object> getOrder(Restaurant restaurant){
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		List<Order> list=orderDao.getOrderByRestaurantUsername(restaurant.getRestaurantAccountInformation().getUsername());
		
		List<Order> listCopy=new ArrayList<>();
		
		for(Order o:list) {
			if(o.getOrderState().equals("need pay")) {
				continue;
			}
			Order oCopy=new Order();
			
			oCopy.setOrderID(o.getOrderID());
			
			oCopy.setOrderTime(o.getOrderTime());
			
			Restaurant r=new Restaurant();
			
			r.setRestaurantName(o.getRestaurant().getRestaurantName());
			
			oCopy.setRestaurant(r);
			
			oCopy.setOrderPrice(o.getOrderPrice());
			
			switch(o.getOrderState()) {
			case "need carrier":
				oCopy.setOrderState("等待分配送餐人员,可以暂缓出餐");
				break;
			case "to restaurant":
				oCopy.setOrderState("送餐人员正在赶往商家，请尽快出餐");
				break;
			case "get dishes":
				oCopy.setOrderState("送餐人员已经取到餐");
				break;
			case "finish":
				oCopy.setOrderState("送餐完毕");
				break;
			}
			
			Carrier c=new Carrier();
			
			if(o.getCarrier()!=null)
				c.setCarrierName(o.getCarrier().getCarrierName());
			else
				c.setCarrierName("暂无");
			
			oCopy.setCarrier(c);
			
			listCopy.add(oCopy);
		}
		
		result.put("Result", "Success");
		result.put("ResultList", listCopy);
		
		return result;
	}
	
	/**
	 * 注册餐馆用户
	 */
	@Override
	public Map<String,Object> register(String restaurantName,String restaurantAddress,String restaurantPhone,String username,String password,String nickname,String eMailAddress){
		Map<String,Object> result=new HashMap<>();
		
		if(restaurantName==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantName is null");
			return result;
		}if(restaurantAddress==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantAddress is null");
			return result;
		}if(restaurantPhone==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantPhone is null");
			return result;
		}if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}if(password==null) {
			result.put("Result", "Error");
			result.put("Reason", "Password is null");
			return result;
		}if(nickname==null) {
			result.put("Result", "Error");
			result.put("Reason", "Nickname is null");
			return result;
		}if(eMailAddress==null) {
			result.put("Result", "Error");
			result.put("Reason", "EMailAddress is null");
			return result;
		}
		
		if(userServiceImpl.checkUsernameExist(username)) {
			result.put("Result", "Error");
			result.put("Reason", "Username exists");
			return result;
		}
		
		String activationCode=RandomStr.randomStr(10);
		
		AccountInformation accountInformation=new AccountInformation();
		
		accountInformation.setUsername(username);
		accountInformation.setPassword(password);
		accountInformation.setNickname(nickname);
		accountInformation.seteMailAddress(eMailAddress);
		
		accountInformation.setActivationCode(activationCode);
		accountInformation.setActivationState("no");
		accountInformation.setAccountBalance(0.0);
		
		Restaurant restaurant=new Restaurant();
		
		restaurant.setRestaurantName(restaurantName);
		restaurant.setRestaurantState("close");
		restaurant.setRestaurantAddress(restaurantAddress);
		restaurant.setRestaurantPhone(restaurantPhone);
		restaurant.setRestaurantAccountInformation(accountInformation);
		restaurant.setMenus(new ArrayList<>());
		restaurant.setVoucherActivities(new ArrayList<>());
		restaurant.setRequiringDiscounts(new ArrayList<>());
		
		restaurantDao.saveRestaurant(restaurant);
		
		if(!EmailSender.sendActivationEmail(username, activationCode, eMailAddress)){
			result.put("Result", "SuccessWithError");
			result.put("Reason", "Email send error");
			return result;
		}
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 添加满减（没有用到，因为没有做满减部分）
	 */
	@Override
	public Map<String,Object> setRequiringDiscount(Restaurant restaurant,Double requiringMoney,Double discountMoney){
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}if(requiringMoney==null) {
			result.put("Result", "Error");
			result.put("Reason", "RequiringMoney is null");
			return result;
		}if(discountMoney==null) {
			result.put("Result", "Error");
			result.put("Reason", "DiscountMoney is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		RequiringDiscount requiringDiscount=new RequiringDiscount();
		
		requiringDiscount.setRestaurant(restaurant);
		requiringDiscount.setRequiringMoney(requiringMoney);
		requiringDiscount.setDiscountMoney(discountMoney);
		
		requiringDiscountDao.saveRequiringDiscount(requiringDiscount);
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 删除满减（没有用到，因为没有做满减部分）
	 */
	@Override
	public Map<String,Object> deleteRequiringDiscount(Restaurant restaurant, Long requiringDiscountID){
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}if(requiringDiscountID==null) {
			result.put("Result", "Error");
			result.put("Reason", "RequiringDiscountID is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		RequiringDiscount requiringDiscount=requiringDiscountDao.getRequiringDiscountById(requiringDiscountID);
		
		if(requiringDiscount==null) {
			result.put("Result", "Error");
			result.put("Reason", "RequiringDiscountID is incorrect");
			return result;
		}
		
		if(!requiringDiscount.getRestaurant().equals(restaurant)) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to delete this menu");
		}
		
		requiringDiscountDao.deleteRequiringDiscount(requiringDiscount);
		
		result.put("Result", "Success");
		return result;
	}

	/**
	 * 获取餐馆所有的菜单
	 */
	@Override
	public Map<String, Object> getMenu(Restaurant restaurant) {
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		List<Menu> menuList=menuDao.getMenuByRestaurant(restaurant);
		
		result.put("Result", "Success");
		result.put("MenuList", menuList);
		return result;
	}

	/**
	 * 获取订单详细信息
	 */
	@Override
	public Map<String, Object> getOrderDetail(Restaurant restaurant, Long orderId) {
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Order order=orderDao.getOderById(orderId);
		
		if(!order.getRestaurant().getRestaurantID().equals(restaurant.getRestaurantID())){
			result.put("Result", "Error");
			result.put("Reason", "This restaurant has no right to access this order");
		}
		
		Order orderNeed=new Order();
		
		orderNeed.setRestaurant(new Restaurant());
		
		switch(order.getOrderState()) {
		case "need pay":
			orderNeed.setOrderState("等待支付");
			break;
		case "need carrier":
			orderNeed.setOrderState("等待分配送餐人员");
			break;
		case "to restaurant":
			orderNeed.setOrderState("送餐人员正在赶往商家");
			break;
		case "get dishes":
			orderNeed.setOrderState("送餐人员已经取到餐");
			break;
		case "finish":
			orderNeed.setOrderState("送餐完毕");
			break;
		}
		
		Carrier c=new Carrier();
		
		if(order.getCarrier()!=null)
			c.setCarrierName(order.getCarrier().getCarrierName());
		else
			c.setCarrierName("暂无");
		
		orderNeed.setCarrier(c);
		
		orderNeed.getRestaurant().setRestaurantName(order.getRestaurant().getRestaurantName());
		
		ArrayList<MenuOrder> mol=new ArrayList<>();
		
		for(MenuOrder mo:order.getDishes()) {
			MenuOrder moc=new MenuOrder();
			
			Menu menu=new Menu();
			
			menu.setMenuName(mo.getMenu().getMenuName());
			
			moc.setMenu(menu);
			
			moc.setNumber(mo.getNumber());
			
			mol.add(moc);
		}
		
		orderNeed.setDishes(mol);
		
		orderNeed.setOrderPrice(order.getOrderPrice());
		
		result.put("Result", "Success");
		result.put("Order", orderNeed);
		return result;
	}

	/**
	 * 获取餐馆状态，用于展示并修改
	 */
	@Override
	public Map<String, Object> getState(Restaurant restaurant) {
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}
		
		Restaurant restaurantx=restaurantDao.getRestaurantById(restaurant.getRestaurantID());
		
		result.put("Result", "Success");
		result.put("State", restaurantx.getRestaurantState());
		return result;
	}

	/**
	 * 修改信息
	 */
	@Override
	public Map<String, Object> changeInformation(Restaurant restaurant, String restaurantName, String restaurantAddress,
			String restaurantPhone) {
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}if(restaurantName==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantName is null");
			return result;
		}if(restaurantAddress==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantAddress is null");
			return result;
		}if(restaurantPhone==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantPhone is null");
			return result;
		}
		
		Restaurant restaurantx=restaurantDao.getRestaurantById(restaurant.getRestaurantID());
		
		restaurantx.setRestaurantName(restaurantName);
		
		restaurantx.setRestaurantAddress(restaurantAddress);
		
		restaurantx.setRestaurantPhone(restaurantPhone);
		
		restaurantDao.saveOrUpdateRestaurant(restaurantx);
		
		result.put("Result", "Success");
		return result;
	}

	/**
	 * 获取信息展示并用于修改
	 */
	@Override
	public Map<String, Object> getInformation(Restaurant restaurant) {
		Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}
		
		Restaurant restaurantx=restaurantDao.getRestaurantById(restaurant.getRestaurantID());
		
		result.put("Result", "Success");
		result.put("Restaurant", restaurantx);
		return result;
	}

	
	@Override
	public Map<String, Object> GiveCommentToCustomer(Restaurant restaurant, String customerUsername, String comment,
			Integer point) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#GiveCommentToCustomer(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	/*@Override
	public Map<String,Object> GiveCommentToCustomer(Restaurant restaurant,String customerUsername,String comment,Integer point){
	Map<String,Object> result=new HashMap<>();
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant is null");
			return result;
		}if(customerUsername==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerUsername is null");
			return result;
		}if(comment==null) {
			result.put("Result", "Error");
			result.put("Reason", "Comment is null");
			return result;
		}if(point==null) {
			result.put("Result", "Error");
			result.put("Reason", "Point is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		//加上只有接过单才能给评论的限制
		List<Order> orderlist=orderDao.getOrderByRestaurantUsernameAndCustomerUsername(restaurant.getRestaurantAccountInformation().getUsername(), customerUsername);
		
		if(orderlist.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to comment on this customer");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(restaurant.getRestaurantAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Customer customer=customerDao.getCustomerByUsername(customerUsername);
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer's username is incorrect");
			return result;
		}
	
		commentFromRestaurantToCustomerDao.addCommentFromRestaurantToCustomer(restaurant, customer, comment,point);
	
		result.put("Result", "Success");
		
		return result;
	}*/
	
}
