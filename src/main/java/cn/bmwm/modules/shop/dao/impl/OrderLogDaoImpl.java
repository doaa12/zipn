/*


 * */
package cn.bmwm.modules.shop.dao.impl;


import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.OrderLogDao;
import cn.bmwm.modules.shop.entity.OrderLog;

/**
 * Dao - 订单日志
 * 
 *
 * @version 1.0
 */
@Repository("orderLogDaoImpl")
public class OrderLogDaoImpl extends BaseDaoImpl<OrderLog, Long> implements OrderLogDao {

}