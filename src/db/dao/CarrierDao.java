package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Carrier;

@Repository("carrierDao")
public class CarrierDao {

	@Resource
	private BaseDao<Carrier> baseDao;
	
	public Carrier getCarrierByUsername(String username) {
		return baseDao.get("select c from Carrier c where c.carrierAccountInformation.username=?0", new Object[] {username}, Carrier.class);
	}
	
	public void saveCarrier(Carrier carrier) {
		baseDao.save(carrier);
	}
	
	public void saveOrUpdateCarrier(Carrier carrier) {
		baseDao.saveOrUpdate(carrier);
	}
	
}
