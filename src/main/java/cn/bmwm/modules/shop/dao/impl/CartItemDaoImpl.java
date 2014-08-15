/*


 * */
package cn.bmwm.modules.shop.dao.impl;


import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.CartItemDao;
import cn.bmwm.modules.shop.entity.CartItem;

/**
 * Dao - 购物车项
 * 
 *
 * @version 1.0
 */
@Repository("cartItemDaoImpl")
public class CartItemDaoImpl extends BaseDaoImpl<CartItem, Long> implements CartItemDao {

}