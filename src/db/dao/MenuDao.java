package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Menu;
import db.entity.Restaurant;

@Repository("menuDao")
public class MenuDao {

	@Resource
	private BaseDao<Menu> baseDao;
	
	public void saveMenu(Menu menu) {
		baseDao.save(menu);
	}
	
	public void deleteMenu(Menu menu) {
		baseDao.delete(menu);
	}
	
	public Menu getMenuById(Long id) {
		return baseDao.get(Menu.class, id);
	}
	
	public List<Menu> getMenuByRestaurant(Restaurant r){
		return baseDao.find("select m from Menu m where m.restaurant.restaurantID=?0", new Object[] {r.getRestaurantID()}, Menu.class);
	}
	
	
}
