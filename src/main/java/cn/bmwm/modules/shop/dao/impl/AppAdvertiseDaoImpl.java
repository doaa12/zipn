package cn.bmwm.modules.shop.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.AppAdvertiseDao;
import cn.bmwm.modules.shop.entity.AppAdvertise;

/**
 * Dao -- App广告
 * @author zby
 * 2014-9-13 上午9:25:17
 */
@Repository("appAdvertiseDaoImpl")
public class AppAdvertiseDaoImpl extends BaseDaoImpl<AppAdvertise,Long> implements AppAdvertiseDao {

	/**
	 * 获取城市广告
	 * @param city
	 * @param type：广告类型，1：首页顶部，2：首页中部，3：顶级分类下顶部
	 * @return
	 */
	public List<AppAdvertise> findByCity(String city, Integer type) {
		String jpql = " select ad from AppAdvertise ad where ad.city like :city and ad.type = :type and ad.isEnabled = true order by ad.city,ad.order";
		TypedQuery<AppAdvertise> query = entityManager.createQuery(jpql, AppAdvertise.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("city", "%" + city + "%").setParameter("type", type).getResultList();
	}
	
}
