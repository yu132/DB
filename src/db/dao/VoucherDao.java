package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Voucher;

@Repository("voucherDao")
public class VoucherDao {

	@Resource
	private BaseDao<Voucher> baseDao;
	
	public Voucher getVoucherById(Long id) {
		return baseDao.get(Voucher.class, id);
	}
	
}
