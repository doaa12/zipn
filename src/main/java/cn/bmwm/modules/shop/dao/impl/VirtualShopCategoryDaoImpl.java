/**
 * 
 */
package cn.bmwm.modules.shop.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.VirtualShopCategoryDao;
import cn.bmwm.modules.shop.entity.VirtualShopCategory;

/**
 * Dao -- 虚拟店铺分类
 * @author zhoupuyue
 * @date 2014-9-11
 */
@Repository("virtualShopCategoryDaoImpl")
public class VirtualShopCategoryDaoImpl extends BaseDaoImpl<VirtualShopCategory,Long> implements VirtualShopCategoryDao {

	/**
	 * 查询虚拟店铺分类
	 * @param city
	 * @return
	 */
	public List<VirtualShopCategory> findList(String city) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<VirtualShopCategory> cq = cb.createQuery(VirtualShopCategory.class);
		Root<VirtualShopCategory> root = cq.from(VirtualShopCategory.class);
		cq.select(root);
		Predicate restrictions = cb.conjunction();
		
		if(city != null && !city.trim().equals("")) {
			restrictions = cb.and(restrictions, cb.like(root.<String>get("city"), "%" + city + "%"));
		}
		
		cq.where(restrictions);
		
		List<Order> orders = new ArrayList<Order>();
		orders.add(cb.asc(root.get("city")));
		orders.add(cb.asc(root.get("order")));
		cq.orderBy(orders);
		
		return super.findList(cq, 0, null, null, null);
		
	}
	
}
