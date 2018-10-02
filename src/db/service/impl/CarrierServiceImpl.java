package db.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import db.dao.AccountInformationDao;
import db.dao.CarrierDao;
import db.dao.CommentFromCarrierToCustomerDao;
import db.dao.CustomerDao;
import db.dao.OrderDao;
import db.entity.AccountInformation;
import db.entity.Carrier;
import db.entity.CommentFromCarrierToCustomer;
import db.entity.Customer;
import db.entity.CustomerReceivingInformation;
import db.entity.Location;
import db.entity.Menu;
import db.entity.MenuOrder;
import db.entity.Order;
import db.entity.Restaurant;
import db.service.CarrierService;
import db.service.UserService;
import db.util.distance.DistanceCalculator;
import db.util.randomstr.RandomStr;
import db.util.sendEmail.EmailSender;

@Transactional
@Service("carrierService")
public class CarrierServiceImpl implements CarrierService {
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private CommentFromCarrierToCustomerDao commentFromCarrierToCustomerDao;
	
	@Resource
	private CustomerDao customerDao;
	
	@Resource
	private CarrierDao carrierDao;
	
	@Resource
	private	AccountInformationDao accountInformationDao;
	
	@Resource
	private UserService userServiceImpl;
	
	@Resource
	private DistanceCalculator distanceCalculator;
	
	/**
	 * 获取骑手接的所有订单
	 */
	@Override
	public Map<String,Object> getOrder(Carrier carrier){
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;	
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		List<Order> list=orderDao.getOrderByCarrierUsername(carrier.getCarrierAccountInformation().getUsername());
		
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
			
			CustomerReceivingInformation customerReceivingInformation=new CustomerReceivingInformation();
			
			Customer c=new Customer();
			
			c.setCustomerID(o.getCustomerReceivingInformation().getCustomer().getCustomerID());
			
			customerReceivingInformation.setCustomerAddress(o.getCustomerReceivingInformation().getCustomerAddress());
			
			customerReceivingInformation.setCustomerName(o.getCustomerReceivingInformation().getCustomerName());

			customerReceivingInformation.setCustomerPhone(o.getCustomerReceivingInformation().getCustomerPhone());
			
			customerReceivingInformation.setCustomer(c);
			
			oCopy.setCustomerReceivingInformation(customerReceivingInformation);
			
			oCopy.setOrderTime(o.getOrderTime());
			
			listCopy.add(oCopy);
		}
		
		result.put("Result", "Success");
		result.put("ResultList", listCopy);
		
		return result;
	};

	/**
	 * 给客户评价
	 */
	@Override
	public Map<String,Object> giveCommentToCustomer(Carrier carrier,Long customerId,String comment,Integer point){
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}if(customerId==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerId is null");
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
		
		//加上只有接过单才能给评论的限制
		List<Order> orderlist=orderDao.getOrderByCarrierUsernameAndCustomerUsername(carrier.getCarrierID(), customerId);
		
		if(orderlist.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to comment on this customer");
			return result;
		}
		
		Customer customer=customerDao.getCustomerById(customerId);
		
		if(customer==null) {
			result.put("Result", "Error");
			result.put("Reason", "Customer's username is incorrect");
			return result;
		}
	
		commentFromCarrierToCustomerDao.addCommentFromCarrierToCustomer(carrier, customer, comment,point);
	
		result.put("Result", "Success");
		
		return result;
	};
	
	/**
	 * 注册骑手账号
	 */
	@Override
	public Map<String,Object> register(String name,String phone,String username,String password,String nickname,String eMailAddress){
		
		Map<String,Object> result=new HashMap<>();
		
		if(name==null) {
			result.put("Result", "Error");
			result.put("Reason", "Name is null");
			return result;
		}if(phone==null) {
			result.put("Result", "Error");
			result.put("Reason", "Phone is null");
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
		
		Carrier carrier=new Carrier();
		
		carrier.setCarrierName(name);
		carrier.setCarrierState("rest");
		carrier.setCarrierPhone(phone);
		carrier.setCarrierAccountInformation(accountInformation);
		
		carrierDao.saveCarrier(carrier);
		
		if(!EmailSender.sendActivationEmail(username, activationCode, eMailAddress)){
			result.put("Result", "SuccessWithError");
			result.put("Reason", "Email send error");
			return result;
		}
		
		result.put("Result", "Success");
		return result;
	};
	
	/**
	 * 将工作状态切换到工作
	 */
	@Override
	public Map<String,Object> start(Carrier carrier){
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		carrier=carrierDao.getCarrierbyId(carrier.getCarrierID());
		
		carrier.setCarrierState("work");
		
		carrierDao.saveOrUpdateCarrier(carrier);
		
		result.put("Result", "Success");
		return result;
	};
	
	/**
	 * 将工作状态切换到休息
	 */
	@Override
	public Map<String,Object> stop(Carrier carrier){
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		carrier=carrierDao.getCarrierbyId(carrier.getCarrierID());
		
		carrier.setCarrierState("rest");
		
		carrierDao.saveOrUpdateCarrier(carrier);
		
		result.put("Result", "Success");
		return result;
	};
	
	/**
	 * 获取周围可接的订单
	 */
	@Override
	public Map<String,Object> getAvailableOrder(Carrier carrier,String location){
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}if(location==null){
			result.put("Result", "Error");
			result.put("Reason", "Location is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		//需要算出两者距离，选出比较适合的单子来接，太远了就不适合了
		
		List<Order> nearOrder=new ArrayList<>();
		
		Location userLoc=distanceCalculator.getOrNewLocation(location);
		
		if(userLoc==null) {
			result.put("Result", "Error");
			result.put("Reason", "User location lat & lng get error");
			return result;
		}
		
		bl:
		for(long i=0;;i++) {
			List<Order> l=orderDao.getOrderByState("need carrier",i*1000, (i+1)*1000-1);
			
			if(l.size()==0)
				break;
			for(Order o:l) {
				Location restaurantLoc=distanceCalculator.getOrNewLocation(o.getRestaurant().getRestaurantAddress());
				
				if(restaurantLoc==null)
					continue;
				
				if(distanceCalculator.getDistance(userLoc.getLat(), userLoc.getLng(), restaurantLoc.getLat(), restaurantLoc.getLng())<5100) {
					Order orderNeed=new Order();
					
					orderNeed.setOrderID(o.getOrderID());
					
					orderNeed.setOrderTime(o.getOrderTime());
					
					orderNeed.setRestaurant(new Restaurant());
					
					Order order=o;
					
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
					
					CustomerReceivingInformation cri=new CustomerReceivingInformation();
					
					cri.setCustomerName(order.getCustomerReceivingInformation().getCustomerName());
					
					cri.setCustomerPhone(order.getCustomerReceivingInformation().getCustomerPhone());
					
					cri.setCustomerAddress(order.getCustomerReceivingInformation().getCustomerAddress());
					
					Customer customer=new Customer();
					
					customer.setCustomerID(order.getCustomerReceivingInformation().getCustomer().getCustomerID());
					
					cri.setCustomer(customer);
					
					orderNeed.setCustomerReceivingInformation(cri);
					
					orderNeed.setCarrierMoney(order.getCarrierMoney());
					
					nearOrder.add(orderNeed);
				}
				if(nearOrder.size()>30)
					break bl;
			}
		}
		
		result.put("Result", "Success");
		result.put("OrderList", nearOrder);
		return result;
	}
	
	/**
	 * 获取某个用户收到的评价
	 */
	@Override
	public Map<String,Object> getCustomerComment(Carrier carrier,Long customerId){
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}if(customerId==null){
			result.put("Result", "Error");
			result.put("Reason", "CustomerId is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		List<CommentFromCarrierToCustomer> list=commentFromCarrierToCustomerDao.getCommentFromCarrierToCustomerByCustomerId(customerId);
		
		List<CommentFromCarrierToCustomer> listCopy=new ArrayList<>();
		
		for(CommentFromCarrierToCustomer c:list) {
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
	 * 获取某个订单的详细信息（没有用到，因为我认为骑手不需要知道详细的订单细节）
	 */
	@Override
	public Map<String, Object> getOrderDetail(Carrier carrier, Long orderId) {
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
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
		
		if(order.getCarrier().getCarrierID()!=carrier.getCarrierID()) {
			result.put("Result", "Error");
			result.put("Reason", "This account has no right to pay this order");
			return result;
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
	 * 接单
	 */
	@Override
	public Map<String, Object> takeOrder(Carrier carrier, Long orderId) {
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}if(orderId==null) {
			result.put("Result", "Error");
			result.put("Reason", "OrderId is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
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
		
		if(!order.getOrderState().equals("need carrier")) {
			result.put("Result", "Error");
			result.put("Reason", "Order state is not \"need carrier\"");
			return result;
		}
		
		order.setOrderState("to restaurant");
		
		order.setCarrier(carrierDao.getCarrierbyId(carrier.getCarrierID()));
		
		orderDao.saveOrUpdateOrder(order);
		
		result.put("Result", "Success");
		return result;
	}

	/**
	 * 获取当前状态，用于查看和修改
	 */
	@Override
	public Map<String, Object> getState(Carrier carrier) {
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Carrier carrierx=carrierDao.getCarrierbyId(carrier.getCarrierID());
		
		result.put("Result", "Success");
		result.put("State", carrierx.getCarrierState());
		return result;
	}

	/**
	 * 获取信息，用于查看和修改
	 */
	@Override
	public Map<String, Object> getInformation(Carrier carrier) {
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Carrier carrierx=carrierDao.getCarrierbyId(carrier.getCarrierID());
		
		result.put("Result", "Success");
		result.put("Carrier", carrierx);
		return result;
	}

	/**
	 * 修改信息
	 */
	@Override
	public Map<String, Object> changeInformation(Carrier carrier, String carrierPhone) {
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}if(carrierPhone==null) {
			result.put("Result", "Error");
			result.put("Reason", "CarrierPhone is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Carrier carrierx=carrierDao.getCarrierbyId(carrier.getCarrierID());
		
		carrierx.setCarrierPhone(carrierPhone);
		
		carrierDao.saveOrUpdateCarrier(carrierx);
		
		result.put("Result", "Success");
		return result;
	}

	/**
	 * 获取之前对某个客户评价，用于修改评价
	 */
	@Override
	public Map<String, Object> getCommentToCustomer(Carrier carrier, Long customerId) {
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}if(customerId==null) {
			result.put("Result", "Error");
			result.put("Reason", "CustomerId is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		CommentFromCarrierToCustomer commentFromCarrierToCustomer = 
				commentFromCarrierToCustomerDao.getCommentFromCarrierToCustomerByCarrierIdAndCustomerId(carrier.getCarrierID(), customerId);
		
		if(commentFromCarrierToCustomer==null) {
			Customer c=customerDao.getCustomerById(customerId);
			if(c==null) {
				result.put("Result", "Error");
				result.put("Reason", "Custoemr not exists");
				return result;
			}
			result.put("Name", c.getCustomerAccountInformation().getNickname());
		}else
			result.put("Name", commentFromCarrierToCustomer.getCustomer().getCustomerAccountInformation().getNickname());
		
		result.put("Result", "Success");
		
		result.put("Comment", commentFromCarrierToCustomer);
		return result;
	}

	/**
	 * 将订单的状态切换到骑手已经取到餐的状态
	 */
	@Override
	public Map<String, Object> changeOrderStateToGetDishes(Carrier carrier, Long orderId) {
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}if(orderId==null) {
			result.put("Result", "Error");
			result.put("Reason", "OrderId is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Order o=orderDao.getOderById(orderId);
		
		if(!o.getCarrier().getCarrierID().equals(carrier.getCarrierID())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not allow to change this order's state");
			return result;
		}
		
		o.setOrderState("get dishes");
		
		orderDao.saveOrUpdateOrder(o);
		
		AccountInformation ai=o.getRestaurant().getRestaurantAccountInformation();
		
		ai.setAccountBalance(ai.getAccountBalance()+o.getOrderPrice()-o.getCarrierMoney());
		
		accountInformationDao.saveOrUpdateAccountInformation(ai);
		
		result.put("Result", "Success");
		return result;
	}

	/**
	 * 将订单的状态切换到客户已收货的状态
	 */
	@Override
	public Map<String, Object> changeOrderStateToFinish(Carrier carrier, Long orderId) {
		Map<String,Object> result=new HashMap<>();
		
		if(carrier==null) {
			result.put("Result", "Error");
			result.put("Reason", "Carrier is null");
			return result;
		}if(orderId==null) {
			result.put("Result", "Error");
			result.put("Reason", "OrderId is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(carrier.getCarrierAccountInformation().getUsername())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		Order o=orderDao.getOderById(orderId);
		
		if(!o.getCarrier().getCarrierID().equals(carrier.getCarrierID())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not allow to change this order's state");
			return result;
		}
		
		o.setOrderState("finish");
		
		orderDao.saveOrUpdateOrder(o);
		
		AccountInformation ai=o.getCarrier().getCarrierAccountInformation();
		
		ai.setAccountBalance(ai.getAccountBalance()+o.getCarrierMoney());
		
		accountInformationDao.saveOrUpdateAccountInformation(ai);
		
		result.put("Result", "Success");
		return result;
	}
	
}
