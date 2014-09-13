package cn.bmwm.modules.shop.service;

import java.util.List;

import cn.bmwm.modules.shop.entity.AppAdvertise;

/**
 * Service -- App广告
 * @author zby
 * 2014-9-13 上午9:20:47
 */
public interface AppAdvertiseService extends BaseService<AppAdvertise,Long> {

	/**
	 * 获取城市广告
	 * @param city
	 * @return
	 */
	List<AppAdvertise> findByCity(String city);
	
}
