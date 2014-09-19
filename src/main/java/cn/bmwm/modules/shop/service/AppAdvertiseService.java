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
	 * @param type：广告类型，1：首页顶部，2：首页中部，3：顶级分类下顶部
	 * @return
	 */
	List<AppAdvertise> findByCity(String city, Integer type);
	
}
