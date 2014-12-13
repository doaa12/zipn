/*


 * */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.ShippingMethodDao;
import cn.bmwm.modules.shop.entity.ShippingMethod;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.ShippingMethodService;

/**
 * Service - 配送方式
 * 
 *
 * @version 1.0
 */
@Service("shippingMethodServiceImpl")
public class ShippingMethodServiceImpl extends BaseServiceImpl<ShippingMethod, Long> implements ShippingMethodService {
	
	@Resource(name = "shippingMethodDaoImpl")
	private ShippingMethodDao shippingMethodDao;

	@Resource(name = "shippingMethodDaoImpl")
	public void setBaseDao(ShippingMethodDao shippingMethodDao) {
		super.setBaseDao(shippingMethodDao);
	}
	
	/**
	 * 按店铺查询运送方式设置
	 * @param shop
	 * @return
	 */
	public ShippingMethod findByShop(Shop shop) {
		return shippingMethodDao.findByShop(shop);
	}

}