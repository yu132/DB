package db.test;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionSupport;

import db.service.CarrierService;
import db.service.RestaurantService;

public class TestAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	@Resource
	private CarrierService carrierService;
	
	@Resource
	private RestaurantService restaurantService;
	
	@Override
	public String execute() throws Exception {
		
	//	carrierService.register("�ඨ��", "15098796072", "yudingbang", "yudingbang1998", "yu1234", "876633022@qq.com");
		
	//	restaurantService.register("��ƽ����", "����·16��", "15098796072", "yudingbang", "yudingbang1998", "yu1234", "876633022@qq.com");
		
	//	restaurantService.addMenu("yudingbang", "����", 100.0, 0.5);
		
		restaurantService.deleteMenu("yudingbang", 2l);
		
		return SUCCESS;
	}

}
