/*


 * */
package cn.bmwm.modules.shop.dao;

import cn.bmwm.modules.shop.entity.ShippingMethod;
import cn.bmwm.modules.shop.entity.Shop;

/**
 * Dao - 配送方式
 * 
 *
 * @version 1.0
 */
public interface ShippingMethodDao extends BaseDao<ShippingMethod, Long> {
	
	/**
	 * 按店铺查询运送方式设置
	 * @param shop
	 * @return
	 */
	ShippingMethod findByShop(Shop shop);

}