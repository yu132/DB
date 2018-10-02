package db.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import db.action.customerAction.PlaceOrder.MenuIdAndNumber;
import db.dao.AccountInformationDao;
import db.dao.CarrierDao;
import db.dao.CommentFromCustomerToCarrierDao;
import db.dao.CommentFromCustomerToRestaurantDao;
import db.dao.CustomerDao;
import db.dao.CustomerReceivingInformationDao;
import db.dao.MenuDao;
import db.dao.OrderDao;
import db.dao.RestaurantDao;
import db.dao.VoucherDao;
import db.entity.AccountInformation;
import db.entity.Carrier;
import db.entity.CommentFromCarrierToCustomer;
import db.entity.CommentFromCustomerToCarrier;
import db.entity.CommentFromCustomerToRestaurant;
import db.entity.Customer;
import db.entity.CustomerReceivingInformation;
import db.entity.Location;
import db.entity.Menu;
import db.entity.MenuOrder;
import db.entity.Order;
import db.entity.Restaurant;
import db.entity.Voucher;
import db.service.CustomerService;
import db.service.UserService;
import db.util.distance.DistanceCalculator;
import db.util.randomstr.RandomStr;
import db.util.sendEmail.EmailSender;

@Transactional
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	
	@Resource
	private DistanceCalculator distanceCalculator;
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private RestaurantDao restaurantDao;
	
	@Resource
	private UserService userServiceImpl;
	
	@Resource
	private CustomerDao customerDao;
	
	@Resource
	private CarrierDao carrierDao;
	
	@Resource
	private VoucherDao voucherDao;
	
	@Resource
	private MenuDao menuDao;
	
	@Resource
	private	AccountInformationDao accountInformationDao;
	
	@Resource
	private CommentFromCustomerToCarrierDao commentFromCustomerToCarrierDao;
	
	@Resource
	private CommentFromCustomerToRestaurantDao commentFromCustomerToRestaurantDao;

	@Resource
	private CustomerReceivingInformationDao customerReceivingInformationDao;
	
	/**
	 * 通过餐馆名字模糊搜索（没有用到）
	 */
	@Override
	public Map<String,Object> getRestaurantInformation(Customer customer,String restaurantName){
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;	
		}if(restaurantName==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantName is null");
			return result;	
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		List<Restaurant> restaurant=restaurantDao.geRestaurantByFuzzyName(restaurantName);
		
		result.put("Result", "Success");
		result.put("RestaurantInformation", restaurant);
		return result;
	}
	
	/**
	 * 通过餐馆ID搜索餐馆信息和其菜单信息
	 */
	@Override
	public Map<String, Object> getRestaurantInformationById(Customer customer, Long id) {
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;	
		}if(id==null) {
			result.put("Result", "Error");
			result.put("Reason", "Id is null");
			return result;	
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Restaurant restaurant=restaurantDao.getRestaurantById(id);
		
		List<Menu> mlist=menuDao.getMenuByRestaurant(restaurant);
		
		result.put("Result", "Success");
		result.put("RestaurantInformation", restaurant);
		result.put("RestaurantMenu", mlist);
		return result;
	}
	
	/**
	 * 通过餐馆ID搜索餐馆信息
	 */
	@Override
	public Map<String, Object> getRestaurantInformationByIdWithoutMenu(Customer customer, Long id){
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;	
		}if(id==null) {
			result.put("Result", "Error");
			result.put("Reason", "Id is null");
			return result;	
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Restaurant restaurant=restaurantDao.getRestaurantById(id);
		
		result.put("Result", "Success");
		result.put("RestaurantInformation", restaurant);
		return result;
	}
	
	/**
	 * 通过客户位置搜索附近餐馆
	 */
	@Override
	public Map<String,Object> getRecommendRestaurant(Customer customer,String userLocation){
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;	
		}if(userLocation==null) {
			result.put("Result", "Error");
			result.put("Reason", "UserLocation is null");
			return result;	
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		List<Restaurant> nearRestaurant=new ArrayList<>();
		
		Location userLoc=distanceCalculator.getOrNewLocation(userLocation);
		
		if(userLoc==null) {
			result.put("Result", "Error");
			result.put("Reason", "User location lat & lng get error");
			return result;
		}
		
		bl:
		for(int i=0;;i++) {
			List<Restaurant> l=restaurantDao.getRestaurantByRange(i*1000, (i+1)*1000-1);
			
			if(l.size()==0)
				break;
			for(Restaurant r:l) {
				if(!r.getRestaurantState().equals("open"))
					continue;
				Location restaurantLoc=distanceCalculator.getOrNewLocation(r.getRestaurantAddress());
				
				if(restaurantLoc==null)
					continue;
				
				if(distanceCalculator.getDistance(userLoc.getLat(), userLoc.getLng(), restaurantLoc.getLat(), restaurantLoc.getLng())<5100) {
					nearRestaurant.add(r);
				}
				if(nearRestaurant.size()>30)
					break bl;
			}
		}
		
		result.put("Result", "Success");
		result.put("NearRestaurant", nearRestaurant);
		return result;
	}
	
	/**
	 * 给骑手评价
	 */
	@Override
	public Map<String,Object> giveCommentToCarrier(Customer customer,Long carrierId,String comment,Integer point){
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;
		}if(carrierId==null) {
			result.put("Result", "Error");
			result.put("Reason", "CarrierId is null");
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
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		//加上只有接过单才能给评论的限制
		List<Order> orderlist=orderDao.getOrderByCarrierUsernameAndCustomerUsername(carrierId, customer.getCustomerID());
		
		if(orderlist.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to comment on this customer");
			return result;
		}
		
		Carrier carrier=carrierDao.getCarrierbyId(carrierId);
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier's username is incorrect");
			return result;
		}
		
		commentFromCustomerToCarrierDao.addCommentFromCustomerToCarrier(customer,carrier, comment, point);
	
		result.put("Result", "Success");
		
		return result;
	}
	
	/**
	 * 给餐馆评价
	 */
	@Override
	public Map<String,Object> giveCommentToRestaurant(Customer customer,Long restaurantId,String comment,Integer point){
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;
		}if(restaurantId==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantId is null");
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
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		//加上只有接过单才能给评论的限制
		List<Order> orderlist=orderDao.getOrderByRestaurantIdAndCustomerId(restaurantId, customer.getCustomerID());
		
		if(orderlist.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to comment on this customer");
			return result;
		}
		
		Restaurant restaurant=restaurantDao.getRestaurantById(restaurantId);
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant's username is incorrect");
			return result;
		}
		
		commentFromCustomerToRestaurantDao.addCommentFromCustomerToRestaurant(customer,restaurant, comment, point);
	
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 下单
	 */
	@Override
	public Map<String, Object> placeOrder(Customer customer,Long time, Long voucherID, Long restaurantID, Long customerReceivingInformationID,
			List<MenuIdAndNumber> dishes,Double money){
		Map<String,Object> result=new HashMap<>();
		
		if(time==null) {
			result.put("Result", "Error");
			result.put("Reason", "Time is null");
			return result;
		}if(restaurantID==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantID is null");
			return result;
		}if(customerReceivingInformationID==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerReceivingInformationID is null");
			return result;
		}
		
		Order order=new Order();
		
		CustomerReceivingInformation customerReceivingInformation=customerReceivingInformationDao.getCustomerReceivingInformationById(customerReceivingInformationID);
		
		if(customerReceivingInformation==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerReceivingInformationID is incorrect");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customerReceivingInformation.getCustomer().getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Voucher voucher=null;
		
		if(voucherID!=null) {
			voucher=voucherDao.getVoucherById(voucherID);
			if(voucher==null) {
				result.put("Result", "Error");
				result.put("Reason", "VoucherID is incorrect");
				return result;
			}
			if(!voucher.getCustomer().getCustomerAccountInformation().getUsername().equals(customerReceivingInformation.getCustomer().getCustomerAccountInformation().getUsername())) {
				result.put("Result", "Error");
				result.put("Reason", "Voucher is not belong to this account");
				return result;
			}
		}
		
		Restaurant restaurant=restaurantDao.getRestaurantById(restaurantID);
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "RestaurantID is incorrect");
			return result;
		}
		
		order.setOrderTime(time);
		order.setVoucher(voucher);
		
		String a1=customerReceivingInformation.getCustomerAddress();
		String a2=restaurant.getRestaurantAddress();
		
		Location l1=distanceCalculator.getOrNewLocation(a1);
		
		Location l2=distanceCalculator.getOrNewLocation(a2);
		
		Double d=distanceCalculator.getDistance(l1.getLat(), l1.getLng(), l2.getLat(), l2.getLng());
		
		Double carrierMoney=Math.round((d/1000)*2)/2.0;
		
		if(carrierMoney<2)
			carrierMoney=2.0;
		
		order.setOrderPrice(money+carrierMoney);
		
		order.setCarrierMoney(carrierMoney);
		
		order.setRestaurant(restaurant);
		order.setCustomerReceivingInformation(customerReceivingInformation);
		order.setCarrier(null);
		order.setOrderState("need pay");
		
		List<MenuOrder> l=new ArrayList<>();
		
		for(int i=0;i<dishes.size();i++) {
			MenuOrder mo=new MenuOrder();
			
			MenuIdAndNumber mnn=dishes.get(i);
			
			mo.setMenu(menuDao.getMenuById(mnn.getMenuId()));
			mo.setNumber(mnn.getMenuNumber());
			mo.setOrder(order);
			
			l.add(mo);
		}
		
		order.setDishes(l);
		
		orderDao.saveOrder(order);
		
		result.put("OrderId", order.getOrderID());
		
		result.put("CarrierMoney", carrierMoney);
		result.put("Money", money);
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 客户注册
	 */
	@Override
	public Map<String,Object> register(String customerPhone,String username,String password,String nickname,String eMailAddress){
		
		
		Map<String,Object> result=new HashMap<>();
		
		if(customerPhone==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerPhone is null");
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
		
		Customer customer=new Customer();
		
		customer.setCustomerPhone(customerPhone);
		customer.setCustomerAccountInformation(accountInformation);
		
		customerDao.saveCustomer(customer);
		
		if(!EmailSender.sendActivationEmail(username, activationCode, eMailAddress)){
			result.put("Result", "SuccessWithError");
			result.put("Reason", "Email send error");
			return result;
		}
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 添加收货信息
	 */
	@Override
	public Map<String,Object> addCustomerReceivingInformation(Customer customer,String customerName,String customerPhone,String customerAddress){
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;
		}if(customerName==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerName is null");
			return result;
		}if(customerPhone==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerPhone is null");
			return result;
		}if(customerAddress==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerAddress is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		CustomerReceivingInformation customerReceivingInformation=new CustomerReceivingInformation();
		
		customerReceivingInformation.setCustomer(customer);
		customerReceivingInformation.setCustomerName(customerName);
		customerReceivingInformation.setCustomerPhone(customerPhone);
		customerReceivingInformation.setCustomerAddress(customerAddress);

		customerReceivingInformationDao.saveCustomerReceivingInformation(customerReceivingInformation);
		
		result.put("Result", "Success");
		return result;
	}
	
	/**
	 * 删除收货信息
	 */
	@Override
	public Map<String,Object> deleteCustomerReceivingInformation(Customer customer,Long customerReceivingInformationID){
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;
		}if(customerReceivingInformationID==null) {
			result.put("Result", "Error");
			result.put("Reason", "customerReceivingInformationID is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		CustomerReceivingInformation customerReceivingInformation=customerReceivingInformationDao.getCustomerReceivingInformationById(customerReceivingInformationID);
		
		if(customerReceivingInformation==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerReceivingInformation not exist");
			return result;
		}
		
		if(customerReceivingInformation.getCustomer().getCustomerID()!=customer.getCustomerID()) {
			result.put("Result", "Error");
			result.put("Reason", "This account has no right to delete this customerReceivingInformation");
			return result;
		}
		
		customerReceivingInformationDao.deleteCustomerReceivingInformation(customerReceivingInformation);
		
		result.put("Result", "Success");
		return result;
	}

	/**
	 * 获取一个客户的全部收货信息
	 */
	@Override
	public Map<String, Object> getCustomerReceivingInformation(Customer customer) {
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		List<CustomerReceivingInformation> clist=customerReceivingInformationDao.getCustomerReceivingInformationByCustomer(customer);
		
		result.put("Result", "Success");
		result.put("CustomerReceivingInformationList", clist);
		return result;
	}
	
	/**
	 * 客户支付订单费用
	 */
	@Override
	public Map<String, Object> payOrder(Customer customer, Long orderId) {
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Order order=orderDao.getOderById(orderId);
		
		if(order==null) {
			result.put("Result", "Error");
			result.put("Reason", "Order not exist");
			return result;
		}
		
		if(order.getCustomerReceivingInformation().getCustomer().getCustomerID()!=customer.getCustomerID()) {
			result.put("Result", "Error");
			result.put("Reason", "This account has no right to pay this order");
			return result;
		}
		
		order.setOrderState("need carrier");
		
		orderDao.saveOrUpdateOrder(order);
		
		Order orderNeed=new Order();
		
		orderNeed.setRestaurant(new Restaurant());
		
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
		
		CustomerReceivingInformation cri=new CustomerReceivingInformation();
		
		cri.setCustomerName(order.getCustomerReceivingInformation().getCustomerName());
		
		cri.setCustomerPhone(order.getCustomerReceivingInformation().getCustomerPhone());
		
		cri.setCustomerAddress(order.getCustomerReceivingInformation().getCustomerAddress());
		
		orderNeed.setCustomerReceivingInformation(cri);
		
		result.put("Order", orderNeed);
		result.put("Result", "Success");
		return result;
	}

	/**
	 * 检查是否所有菜都属于指定的那个餐馆，因为需要限制客户使其不能从多个餐馆点菜却只下一个订单
	 */
	@Override
	public Map<String, Object> checkMenu(Customer customer, List<MenuIdAndNumber> menuList, Long restaurantId) {
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		for(MenuIdAndNumber m:menuList) {
			Menu menu=menuDao.getMenuById(m.getMenuId());
			if(menu==null) {
				result.put("Result", "Error");
				result.put("Reason", "Menu not exist");
				return result;
			}
			if(menu.getRestaurant().getRestaurantID()!=restaurantId) {
				result.put("Result", "Error");
				result.put("Reason", "Menu is not belong to this restaurant");
				return result;
			}
		}
		
		result.put("Result", "Success");
		return result;
	}

	/**
	 * 获取客户全部的订单
	 */
	@Override
	public Map<String, Object> getOrder(Customer customer) {
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		List<Order> list=orderDao.getOrderByCustomerId(customer.getCustomerID());
		
		List<Order> listCopy=new ArrayList<>();
		
		for(Order o:list) {
			Order oCopy=new Order();
			
			oCopy.setOrderID(o.getOrderID());
			
			oCopy.setOrderTime(o.getOrderTime());
			
			Restaurant r=new Restaurant();
			
			r.setRestaurantName(o.getRestaurant().getRestaurantName());
			
			oCopy.setRestaurant(r);
			
			oCopy.setOrderPrice(o.getOrderPrice());
			
			switch(o.getOrderState()) {
			case "need pay":
				oCopy.setOrderState("等待支付");
				break;
			case "need carrier":
				oCopy.setOrderState("等待分配送餐人员");
				break;
			case "to restaurant":
				oCopy.setOrderState("送餐人员正在赶往商家");
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
		result.put("OrderList", listCopy);
		return result;
	}

	/**
	 * 获取一个订单的详细信息
	 */
	@Override
	public Map<String, Object> getOrderDetail(Customer customer, Long orderId) {
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Order order=orderDao.getOderById(orderId);
		
		if(order==null) {
			result.put("Result", "Error");
			result.put("Reason", "Order not exist");
			return result;
		}
		
		if(order.getCustomerReceivingInformation().getCustomer().getCustomerID()!=customer.getCustomerID()) {
			result.put("Result", "Error");
			result.put("Reason", "This account has no right to pay this order");
			return result;
		}
		
		Order orderNeed=new Order();
		
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
		
		if(order.getCarrier()!=null) {
			c.setCarrierName(order.getCarrier().getCarrierName());
			c.setCarrierID(order.getCarrier().getCarrierID());
		}else
			c.setCarrierName("暂无");
		
		
		orderNeed.setCarrier(c);
		
		orderNeed.setRestaurant(new Restaurant());
		
		orderNeed.getRestaurant().setRestaurantName(order.getRestaurant().getRestaurantName());
		
		orderNeed.getRestaurant().setRestaurantID(order.getRestaurant().getRestaurantID());
		
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
		
		CustomerReceivingInformation cri=new CustomerReceivingInformation();
		
		cri.setCustomerName(order.getCustomerReceivingInformation().getCustomerName());
		
		cri.setCustomerPhone(order.getCustomerReceivingInformation().getCustomerPhone());
		
		cri.setCustomerAddress(order.getCustomerReceivingInformation().getCustomerAddress());
		
		orderNeed.setCustomerReceivingInformation(cri);
		
		result.put("Order", orderNeed);
		result.put("Result", "Success");
		return result;
	}

	/**
	 * 获取某个餐馆收到的评价
	 */
	@Override
	public Map<String, Object> getRestaurantComment(Customer customer, Long restaurantId) {
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		List<CommentFromCustomerToRestaurant> list=commentFromCustomerToRestaurantDao.getCommentFromCustomerToRestaurantByRestaurantId(restaurantId);
		
		List<CommentFromCustomerToRestaurant> listCopy=new ArrayList<>();
		
		for(CommentFromCustomerToRestaurant c:list) {
			CommentFromCustomerToRestaurant cp=new CommentFromCustomerToRestaurant();
			
			Restaurant restaurant=new Restaurant();
			
			AccountInformation restaurantAccountInformation=new AccountInformation();
			
			restaurantAccountInformation.setNickname(c.getRestaurant().getRestaurantAccountInformation().getNickname());
			
			restaurant.setRestaurantAccountInformation(restaurantAccountInformation);
			
			cp.setRestaurant(restaurant);
			
			Customer cu=new Customer();
			
			AccountInformation customerAccountInformation=new AccountInformation();
			
			customerAccountInformation.setNickname(c.getCustomer().getCustomerAccountInformation().getNickname());
			
			cu.setCustomerAccountInformation(customerAccountInformation);
			
			cp.setCustomer(cu);
			
			cp.setComment(c.getComment());
			
			cp.setPoint(c.getPoint());
			
			listCopy.add(cp);
		}
		
		result.put("Result", "Success");
		result.put("List", listCopy);
		return result;
	}

	/**
	 * 获取某个骑手收到的评价（没用到）
	 */
	@Override
	public Map<String, Object> getCarrierComment(Customer customer, Long carrierId) {
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		List<CommentFromCustomerToCarrier> list=commentFromCustomerToCarrierDao.getCommentFromCustomerToCarrierByCarrierId(carrierId);
		
		List<CommentFromCarrierToCustomer> listCopy=new ArrayList<>();
		
		for(CommentFromCustomerToCarrier c:list) {
			CommentFromCarrierToCustomer cp=new CommentFromCarrierToCustomer();
			
			Carrier ca=new Carrier();
			
			AccountInformation carrierAccountInformation=new AccountInformation();
			
			carrierAccountInformation.setNickname(c.getCarrier().getCarrierAccountInformation().getNickname());
			
			ca.setCarrierAccountInformation(carrierAccountInformation);
			
			cp.setCarrier(ca);
			
			Customer cu=new Customer();
			
			AccountInformation customerAccountInformation=new AccountInformation();
			
			customerAccountInformation.setNickname(c.getCustomer().getCustomerAccountInformation().getNickname());
			
			cu.setCustomerAccountInformation(customerAccountInformation);
			
			cp.setCustomer(cu);
			
			cp.setComment(c.getComment());
			
			cp.setPoint(c.getPoint());
			
			listCopy.add(cp);
		}
		
		result.put("Result", "Success");
		result.put("List", listCopy);
		return result;
	}

	/**
	 * 获取该用户之前给这个餐馆的评价，用于修改评价
	 */
	@Override
	public Map<String, Object> getCommentToRestaurant(Customer customer, Long restaurantId) {
		Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "customer is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Restaurant restaurant=restaurantDao.getRestaurantById(restaurantId);
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant not exists");
			return result;
		}
		
		CommentFromCustomerToRestaurant commentFromCustomerToRestaurant = 
				commentFromCustomerToRestaurantDao.getCommentFromCustomerToRestaurantByRestaurantIdAndCustomerId(restaurantId,customer.getCustomerID());
		
		result.put("Result", "Success");
		result.put("Name", restaurant.getRestaurantName());
		result.put("Comment", commentFromCustomerToRestaurant);
		
		return result;
	}

	/**
	 * 获取该用户之前给这个骑手的评价，用于修改评价
	 */
	@Override
	public Map<String, Object> getCommentToCarrier(Customer customer, Long carrierId) {
	Map<String,Object> result=new HashMap<>();
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "customer is null");
			return result;
		}if(carrierId==null) {
			result.put("Result", "Error");
			result.put("Reason", "CarrierId is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(customer.getCustomerAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Carrier carrier=carrierDao.getCarrierbyId(carrierId);
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier not exists");
			return result;
		}
		
		CommentFromCustomerToCarrier commentFromCustomerToCarrier = 
				commentFromCustomerToCarrierDao.getCommentFromCustomerToCarrierByCarrierIdAndCustomerId(carrierId, customer.getCustomerID());
		
		result.put("Result", "Success");
		result.put("Name", carrier.getCarrierName());
		result.put("Comment", commentFromCustomerToCarrier);
		return result;
	}
 }
