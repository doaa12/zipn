package cn.bmwm.modules.shop.service;

import java.util.Date;

import cn.bmwm.modules.shop.entity.Cart;
import cn.bmwm.modules.shop.entity.PreOrder;

/**
 * Service -- 预约订单
 * @author zby
 * 2014-12-14 上午10:09:54
 */
public interface PreOrderService extends BaseService<PreOrder,Long> {
	
	/**
	 * 生成预约订单 
	 * @param cart
	 * @param memo
	 * @param bookTime
	 * @param persons
	 * @param contactUserName
	 * @param contactPhone
	 * @return
	 */
	PreOrder build(Cart cart, String memo, Date bookTime, Integer persons, String contactUserName, String contactPhone);
	
	/**
	 * 创建预约订单
	 * @param cart
	 * @param receiver
	 * @param memo
	 * @return
	 */
	PreOrder create(Cart cart, String memo, Date bookTime, Integer persons, String contactUserName, String contactPhone);

}
