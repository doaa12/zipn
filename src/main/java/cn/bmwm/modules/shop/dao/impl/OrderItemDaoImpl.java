/*


 * */
package cn.bmwm.modules.shop.dao.impl;


import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.OrderItemDao;
import cn.bmwm.modules.shop.entity.OrderItem;

/**
 * Dao - 订单项
 * 
 *
 * @version 1.0
 */
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, Long> implements OrderItemDao {

}