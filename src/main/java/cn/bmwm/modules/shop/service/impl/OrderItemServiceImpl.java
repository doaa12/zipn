/*


 * */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.OrderItemDao;
import cn.bmwm.modules.shop.entity.OrderItem;
import cn.bmwm.modules.shop.service.OrderItemService;

/**
 * Service - 订单项
 * 
 *
 * @version 1.0
 */
@Service("orderItemServiceImpl")
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, Long> implements OrderItemService {

	@Resource(name = "orderItemDaoImpl")
	public void setBaseDao(OrderItemDao orderItemDao) {
		super.setBaseDao(orderItemDao);
	}

}