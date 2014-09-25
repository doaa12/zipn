package cn.bmwm.modules.shop.dao;

import java.util.List;

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
	 * @param type：广告类型，1：首页顶部，2：首页中部，3：顶级分类下顶部
	 * @return
	 */
	List<AppAdvertise> findByCity(String city, Integer type);
	
}
