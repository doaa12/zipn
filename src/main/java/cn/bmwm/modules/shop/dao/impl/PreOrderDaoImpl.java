package cn.bmwm.modules.shop.dao.impl;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.PreOrderDao;
import cn.bmwm.modules.shop.entity.PreOrder;

/**
 * Dao -- 预约订单
 * @author zby
 * 2014-12-14 上午10:06:55
 */
@Repository("preOrderDaoImpl")
public class PreOrderDaoImpl extends BaseDaoImpl<PreOrder,Long> implements PreOrderDao {

}
