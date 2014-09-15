package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.ShopActivityDao;
import cn.bmwm.modules.shop.entity.ShopActivity;
import cn.bmwm.modules.shop.service.ShopActivityService;

/**
 * Service -- 店铺活动
 * @author zby
 * 2014-9-15 下午9:21:49
 */
@Service("shopActivityServiceImpl")
public class ShopActivityServiceImpl extends BaseServiceImpl<ShopActivity,Long> implements ShopActivityService {

	@Resource(name = "shopActivityDaoImpl")
	public void setBaseDao(ShopActivityDao shopActivityDao) {
		super.setBaseDao(shopActivityDao);
	}
	
}
