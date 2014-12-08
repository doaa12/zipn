/*


 * */
package cn.bmwm.modules.shop.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;


import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.bmwm.modules.shop.dao.AreaDao;
import cn.bmwm.modules.shop.entity.Area;

/**
 * Dao - 地区
 * 
 *
 * @version 1.0
 */
@Repository("areaDaoImpl")
public class AreaDaoImpl extends BaseDaoImpl<Area, Long> implements AreaDao {
	
	public List<Area> findRoots(Integer count) {
		String jpql = "select area from Area area where area.parent is null order by area.order asc";
		TypedQuery<Area> query = entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}
	
	/**
	 * 查询下一级区域
	 * @param parent
	 * @return
	 */
	public List<Area> findChildren(Area parent) {
		Assert.notNull(parent);
		String jpql = "select area from Area area where area.parent = :parent order by area.order asc";
		return entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT).setParameter("parent", parent).getResultList();
	}

}