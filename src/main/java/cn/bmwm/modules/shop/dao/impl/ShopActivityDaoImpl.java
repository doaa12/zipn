package cn.bmwm.modules.shop.dao.impl;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.ShopActivityDao;
import cn.bmwm.modules.shop.entity.ShopActivity;

/**
 * Dao -- 店铺活动
 * @author zby
 * 2014-9-15 下午9:11:42
 */
@Repository("shopActivityDaoImpl")
public class ShopActivityDaoImpl extends BaseDaoImpl<ShopActivity,Long> implements ShopActivityDao {

}
