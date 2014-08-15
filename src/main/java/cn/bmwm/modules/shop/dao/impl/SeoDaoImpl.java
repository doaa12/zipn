/*


 * */
package cn.bmwm.modules.shop.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;


import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.SeoDao;
import cn.bmwm.modules.shop.entity.Seo;
import cn.bmwm.modules.shop.entity.Seo.Type;

/**
 * Dao - SEO设置
 * 
 *
 * @version 1.0
 */
@Repository("seoDaoImpl")
public class SeoDaoImpl extends BaseDaoImpl<Seo, Long> implements SeoDao {

	public Seo find(Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select seo from Seo seo where seo.type = :type";
			return entityManager.createQuery(jpql, Seo.class).setFlushMode(FlushModeType.COMMIT).setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}