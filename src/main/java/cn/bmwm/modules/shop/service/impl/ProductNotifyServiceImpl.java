/*


 * */
package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.dao.ProductNotifyDao;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductNotify;
import cn.bmwm.modules.shop.service.MailService;
import cn.bmwm.modules.shop.service.ProductNotifyService;

/**
 * Service - 到货通知
 * 
 *
 * @version 1.0
 */
@Service("productNotifyServiceImpl")
public class ProductNotifyServiceImpl extends BaseServiceImpl<ProductNotify, Long> implements ProductNotifyService {

	@Resource(name = "productNotifyDaoImpl")
	ProductNotifyDao productNotifyDao;
	@Resource(name = "mailServiceImpl")
	MailService mailService;

	@Resource(name = "productNotifyDaoImpl")
	public void setBaseDao(ProductNotifyDao ProductNotifyDao) {
		super.setBaseDao(ProductNotifyDao);
	}

	@Transactional(readOnly = true)
	public boolean exists(Product product, String email) {
		return productNotifyDao.exists(product, email);
	}

	@Transactional(readOnly = true)
	public Page<ProductNotify> findPage(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent, Pageable pageable) {
		return productNotifyDao.findPage(member, isMarketable, isOutOfStock, hasSent, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent) {
		return productNotifyDao.count(member, isMarketable, isOutOfStock, hasSent);
	}

	public int send(Long[] ids) {
		List<ProductNotify> productNotifys = findList(ids);
		for (ProductNotify productNotify : productNotifys) {
			mailService.sendProductNotifyMail(productNotify);
			productNotify.setHasSent(true);
			productNotifyDao.merge(productNotify);
		}
		return productNotifys.size();
	}

}