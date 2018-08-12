package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.VoucherActivity;

@Repository("voucherActivityDao")
public class VoucherActivityDao {

	@Resource
	private BaseDao<VoucherActivity> baseDao;
	
	public void saveVoucherActivity(VoucherActivity voucherActivity) {
		baseDao.save(voucherActivity);
	}
	
	public void deleteVoucherActivity(VoucherActivity voucherActivity) {
		baseDao.delete(voucherActivity);
	}
	
	public VoucherActivity getVoucherActivityById(Long id) {
		return baseDao.get(VoucherActivity.class, id);
	}
	
}
