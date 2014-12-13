/*


 * */
package cn.bmwm.modules.shop.service;

import cn.bmwm.modules.shop.entity.ShippingMethod;
import cn.bmwm.modules.shop.entity.Shop;

/**
 * Service - 配送方式
 * 
 *
 * @version 1.0
 */
public interface ShippingMethodService extends BaseService<ShippingMethod, Long> {
	
	/**
	 * 按店铺查询运送方式设置
	 * @param shop
	 * @return
	 */
	ShippingMethod findByShop(Shop shop);

}