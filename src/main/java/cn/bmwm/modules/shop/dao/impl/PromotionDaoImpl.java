/*


 * */
package cn.bmwm.modules.shop.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cn.bmwm.common.persistence.Filter;
import cn.bmwm.common.persistence.Order;
import cn.bmwm.modules.shop.dao.PromotionDao;
import cn.bmwm.modules.shop.entity.Promotion;
import cn.bmwm.modules.shop.entity.Shop;

/**
 * Dao - 促销
 * 
 *
 * @version 1.0
 */
@Repository("promotionDaoImpl")
public class PromotionDaoImpl extends BaseDaoImpl<Promotion, Long> implements PromotionDao {

	public List<Promotion> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
		Root<Promotion> root = criteriaQuery.from(Promotion.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (hasBegun != null) {
			if (hasBegun) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("beginDate").isNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("beginDate"), new Date())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, root.get("beginDate").isNotNull(), criteriaBuilder.greaterThan(root.<Date> get("beginDate"), new Date()));
			}
		}
		if (hasEnded != null) {
			if (hasEnded) {
				restrictions = criteriaBuilder.and(restrictions, root.get("endDate").isNotNull(), criteriaBuilder.lessThan(root.<Date> get("endDate"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("endDate").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("endDate"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}
	
	/**
	 * 查询店铺促销商品数量
	 * @param shop
	 * @return
	 */
	public Long findShopPromotionCount(Shop shop) {
		String jpql = "select count(*) from Promotion promotion where promotion.beginDate <= :date and promotion.endDate >= :date and promotion.shop = :shop ";
		Date date = new Date();
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("date", date).setParameter("shop", shop).getSingleResult();
	}
	
	/**
	 * 查询店铺促销
	 * @param shop
	 * @return
	 */
	public List<Promotion> findShopPromotionList(Shop shop) {
		String jpql = "select promotion from Promotion promotion where promotion.beginDate <= :date and promotion.endDate >= :date and promotion.shop = :shop ";
		Date date = new Date();
		TypedQuery<Promotion> query = entityManager.createQuery(jpql, Promotion.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("date", date).setParameter("shop", shop).getResultList();
	}
	
	/**
	 * 查询店铺促销
	 * @param shop
	 * @param id
	 * @return
	 */
	public Promotion find(Shop shop, Long id) {
		String jpql = "select promotion from Promotion promotion where promotion.id = :id and promotion.shop = :shop";
		TypedQuery<Promotion> query = entityManager.createQuery(jpql, Promotion.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("id", id).setParameter("shop", shop).getSingleResult();
	}

}