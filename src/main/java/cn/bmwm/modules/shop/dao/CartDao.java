/*


 * */
package cn.bmwm.modules.shop.dao;

import cn.bmwm.modules.shop.entity.Cart;

/**
 * Dao - 购物车
 * 
 *
 * @version 1.0
 */
public interface CartDao extends BaseDao<Cart, Long> {

	/**
	 * 清除过期购物车
	 */
	void evictExpired();

}