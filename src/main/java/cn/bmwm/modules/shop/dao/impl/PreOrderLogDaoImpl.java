package cn.bmwm.modules.shop.dao.impl;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.PreOrderLogDao;
import cn.bmwm.modules.shop.entity.PreOrderLog;

/**
 * Dao -- 预约订单日志
 * @author zby
 * 2014-12-14 下午12:05:45
 */
@Repository("preOrderLogDaoImpl")
public class PreOrderLogDaoImpl extends BaseDaoImpl<PreOrderLog,Long> implements PreOrderLogDao {

}
