/**
 * 
 */
package cn.bmwm.modules.shop.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.dao.ShopDao;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;

/**
 * @author zhoupuyue
 * @date 2014-8-22
 */
@Repository("shopDaoImpl")
public class ShopDaoImpl extends BaseDaoImpl<Shop,Long> implements ShopDao {

	/**
	 * 查找店铺分页
	 * @param productCategory
	 * @param city
	 * @param pageable
	 * @return
	 */
	public Page<Shop> findPage(ProductCategory productCategory, String city, Boolean isTop, Boolean isList, Pageable pageable) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Shop> criteriaQuery = criteriaBuilder.createQuery(Shop.class);
		Root<Shop> root = criteriaQuery.from(Shop.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		if(productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productCategory"), productCategory));
		}
		if(city != null && city.trim().length() > 0){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("city"), "%" + city + "%"));
		}
		if(isTop != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isToop"), isTop));
		}
		if(isTop != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		
		return super.findPage(criteriaQuery, pageable);
		
	}
	
	/**
	 * 获取所有开通的城市
	 * @return
	 */
	public List<String> findAllCities() {
		String jpql = "select distinct shop.city from Shop shop ";
		return entityManager.createQuery(jpql, String.class).setFlushMode(FlushModeType.COMMIT).getResultList();
	}
	
}
