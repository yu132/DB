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
import db.entity.Customer;
import db.entity.Menu;
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

	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#addMenu(java.lang.String, java.lang.String, java.lang.Double, java.lang.Double)
	 */
	@Override
	public Map<String,Object> addMenu(String username,String menuName,Double menuPrice,Double menuDiscount){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
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
		
		Restaurant restaurant=restaurantDao.getRestaurantByUsername(username);
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant's username is incorrect");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByAccountInformation(restaurant.getRestaurantAccountInformation())) {
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
		
	//	restaurant.getMenus().add(menu);
		
	//	restaurantDao.saveOrUpdateRestaurant(restaurant);
		
		result.put("Result", "Success");
		return result;
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#deleteMenu(java.lang.String, java.lang.Long)
	 */
	@Override
	public Map<String,Object> deleteMenu(String username,Long menuID){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}if(menuID==null) {
			result.put("Result", "Error");
			result.put("Reason", "MenuID is null");
			return result;
		}
		
		Menu menu=menuDao.getMenuById(menuID);
		
		if(menu==null) {
			result.put("Result", "Error");
			result.put("Reason", "MenuID is incorrect");
			return result;
		}
		
		if(!menu.getRestaurant().getRestaurantAccountInformation().getUsername().equals(username)) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to delete this menu");
		}
		
		menuDao.deleteMenu(menu);
		
		result.put("Result", "Success");
		return result;
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#addVoucherActicity(java.lang.String, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.Long)
	 */
	@Override
	public Map<String,Object> addVoucherActicity(String username,Double needPay,Double discountMoney,Double needToUse,Long validTime){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
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
		
		Restaurant restaurant=restaurantDao.getRestaurantByUsername(username);
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant's username is incorrect");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByAccountInformation(restaurant.getRestaurantAccountInformation())) {
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
	
	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#deleteVoucherActicity(java.lang.String, java.lang.Long)
	 */
	@Override
	public Map<String,Object> deleteVoucherActicity(String username,Long voucherActicityID){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
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
		
		if(!voucherActivity.getRestaurant().getRestaurantAccountInformation().getUsername().equals(username)) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to delete this VoucherActivity");
		}
		
		voucherActivityDao.deleteVoucherActivity(voucherActivity);
		
		result.put("Result", "Success");
		return result;
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#open(java.lang.String)
	 */
	@Override
	public Map<String,Object> open(String username){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}
		
		Restaurant restaurant=restaurantDao.getRestaurantByUsername(username);
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant's username is incorrect");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByAccountInformation(restaurant.getRestaurantAccountInformation())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		restaurant.setRestaurantState("open");
		
		restaurantDao.saveOrUpdateRestaurant(restaurant);
		
		result.put("Result", "Success");
		return result;
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#close(java.lang.String)
	 */
	@Override
	public Map<String,Object> close(String username){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}
		
		Restaurant restaurant=restaurantDao.getRestaurantByUsername(username);
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Restaurant's username is incorrect");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByAccountInformation(restaurant.getRestaurantAccountInformation())) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		restaurant.setRestaurantState("close");
		
		restaurantDao.saveOrUpdateRestaurant(restaurant);
		
		result.put("Result", "Success");
		return result;
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#getOrder(java.lang.String)
	 */
	@Override
	public Map<String,Object> getOrder(String username){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(username)) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		result.put("Result", "Success");
		result.put("ResultList", orderDao.getOrderByRestaurantUsername(username));
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#GiveCommentToCustomer(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Map<String,Object> GiveCommentToCustomer(String username,String customerUsername,String comment,Integer point){
	Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
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
		
		if(!userServiceImpl.checkAccountActivationStateByUsername(username)) {
			result.put("Result", "Error");
			result.put("Reason", "Account is not activated");
			return result;
		}
		
		//加上只有接过单才能给评论的限制
		List<Order> orderlist=orderDao.getOrderByRestaurantUsernameAndCustomerUsername(username, customerUsername);
		
		if(orderlist.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to comment on this customer");
			return result;
		}
		
		Restaurant restaurant=restaurantDao.getRestaurantByUsername(username);
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is incorrect");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByAccountInformation(restaurant.getRestaurantAccountInformation())) {
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
	}
	
	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#register(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
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
	
	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#setRequiringDiscount(java.lang.String, java.lang.Double, java.lang.Double)
	 */
	@Override
	public Map<String,Object> setRequiringDiscount(String username,Double requiringMoney,Double discountMoney){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
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
		
		Restaurant restaurant=restaurantDao.getRestaurantByUsername(username);
		
		if(restaurant==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is incorrect");
			return result;
		}
		
		if(!userServiceImpl.checkAccountActivationStateByAccountInformation(restaurant.getRestaurantAccountInformation())) {
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
	
	/* (non-Javadoc)
	 * @see db.service.impl.RestaurantService#deleteRequiringDiscount(java.lang.String, java.lang.Long)
	 */
	@Override
	public Map<String,Object> deleteRequiringDiscount(String username, Long requiringDiscountID){
		Map<String,Object> result=new HashMap<>();
		
		if(username==null) {
			result.put("Result", "Error");
			result.put("Reason", "Username is null");
			return result;
		}if(requiringDiscountID==null) {
			result.put("Result", "Error");
			result.put("Reason", "RequiringDiscountID is null");
			return result;
		}
		
		RequiringDiscount requiringDiscount=requiringDiscountDao.getRequiringDiscountById(requiringDiscountID);
		
		if(requiringDiscount==null) {
			result.put("Result", "Error");
			result.put("Reason", "RequiringDiscountID is incorrect");
			return result;
		}
		
		if(!requiringDiscount.getRestaurant().getRestaurantAccountInformation().getUsername().equals(username)) {
			result.put("Result", "Error");
			result.put("Reason", "This Account do not have permission to delete this menu");
		}
		
		requiringDiscountDao.deleteRequiringDiscount(requiringDiscount);
		
		result.put("Result", "Success");
		return result;
	}
	
}
