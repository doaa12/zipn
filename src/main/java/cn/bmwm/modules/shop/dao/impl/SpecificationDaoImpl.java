/*


 * */
package cn.bmwm.modules.shop.dao.impl;


import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.SpecificationDao;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.Specification;

/**
 * Dao - 规格
 * 
 *
 * @version 1.0
 */
@Repository("specificationDaoImpl")
public class SpecificationDaoImpl extends BaseDaoImpl<Specification, Long> implements SpecificationDao {
	
	/**
	 * 查询店铺商品规格
	 * @param shop
	 * @return
	 */
	public List<Specification> findShopSpecificationList(Shop shop) {
		String jpql = " select specification from Specification specification where specification.shop = :shop ";
		TypedQuery<Specification> query = entityManager.createQuery(jpql, Specification.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("shop", shop).getResultList();
	}
	
}