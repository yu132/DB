package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Menu;

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
	
	
}
