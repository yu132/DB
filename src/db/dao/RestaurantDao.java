package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Restaurant;

@Repository("restaurantDao")
public class RestaurantDao {
	
	@Resource
	private BaseDao<Restaurant> baseDao;

	public Restaurant getRestaurantById(Long id) {
		return baseDao.get(Restaurant.class, id);
	}
	
	public List<Restaurant> geRestaurantByFuzzyName(String fuzzyName) {
		return baseDao.find("select r from Restaurant r where r.restaurantName like ?", 
				new Object[] {"%"+fuzzyName+"%"}, Restaurant.class);
	}
	
	public Restaurant getRestaurantByUsername(String username) {
		return baseDao.get("select r from Restaurant r where r.carrierAccountInformation.username=?", new Object[] {username}, Restaurant.class);
	}
	
}
