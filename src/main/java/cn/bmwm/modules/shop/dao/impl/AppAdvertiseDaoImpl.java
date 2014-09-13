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
	 * @return
	 */
	public List<AppAdvertise> findByCity(String city) {
		String jpql = " select ad from AppAdvertise ad where ad.city like :city and ad.isEnabled = true ";
		TypedQuery<AppAdvertise> query = entityManager.createQuery(jpql, AppAdvertise.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("city", "%" + city + "%").getResultList();
	}
	
}
