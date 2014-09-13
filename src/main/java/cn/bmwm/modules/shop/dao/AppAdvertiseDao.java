package cn.bmwm.modules.shop.dao;

import cn.bmwm.modules.shop.entity.AppAdvertise;

/**
 * Dao -- App广告
 * @author zby
 * 2014-9-13 上午9:24:06
 */
public interface AppAdvertiseDao extends BaseDao<AppAdvertise,Long> {

	/**
	 * 获取城市广告
	 * @param city
	 * @return
	 */
	AppAdvertise findByCity(String city);
	
}
