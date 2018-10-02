package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.RequiringDiscount;

@Repository("requiringDiscountDao")
public class RequiringDiscountDao {

	@Resource
	private BaseDao<RequiringDiscount> baseDao;
	
	public void saveRequiringDiscount(RequiringDiscount requiringDiscount) {
		baseDao.save(requiringDiscount);
	}
	
	public void saveOrUpdateRequiringDiscount(RequiringDiscount requiringDiscount) {
		baseDao.saveOrUpdate(requiringDiscount);
	}
	
	public void deleteRequiringDiscount(RequiringDiscount requiringDiscount) {
		baseDao.delete(requiringDiscount);
	}
	
	public RequiringDiscount getRequiringDiscountById(Long id) {
		return baseDao.get(RequiringDiscount.class, id);
	}
	
}
