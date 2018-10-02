package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Location;

@Repository("locationDao")
public class LocationDao {

	@Resource
	private BaseDao<Location> baseDao;
	
	public void saveOrUpdateCustomer(Location location) {
		baseDao.saveOrUpdate(location);
	}
	
	public Location getLocation(String locName) {
		return baseDao.get(Location.class, locName);
	}
	
}
