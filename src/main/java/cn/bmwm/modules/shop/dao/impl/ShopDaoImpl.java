/**
 * 
 */
package cn.bmwm.modules.shop.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
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
	 * 后台管理,查找店铺分页
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
	 * 查找店铺列表
	 * @param city : 城市
	 * @param category : 分类
	 * @param page : 页码
	 * @param size : 每页记录数
	 * @return
	 */
	public Map<String,Object> findList(String city, ProductCategory category, int page, int size) {
		
		String jpql = " select count(*) from Shop shop where shop.treePath like :treePath and shop.isList = true and shop.city like :city ";
		TypedQuery<Long> countQuery = entityManager.createQuery(jpql, Long.class);
		countQuery.setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%," + category.getId() + ",%").setParameter("city", "%" + city + "%");
		long total = countQuery.getSingleResult();
		
		jpql = " select shop from Shop shop where shop.treePath like :treePath and shop.isList = true and shop.city like :city order by shop.isTop desc, shop.totalScore / shop.scoreTimes desc ";
		TypedQuery<Shop> listQuery = entityManager.createQuery(jpql, Shop.class);
		listQuery.setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%," + category.getId() + ",%").setParameter("city", "%" + city + "%");
		
		int start = (page - 1) * size;
		listQuery.setFirstResult(start);
		listQuery.setMaxResults(size);
		
		List<Shop> list = listQuery.getResultList();
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("page", page);
		result.put("size", size);
		result.put("total", total);
		result.put("shops", list);
		
		return result;
		
	}
	
	/**
	 * 查找推荐店铺
	 * @param category
	 * @param city
	 * @return
	 */
	public List<Shop> findRecommendList(String city, ProductCategory category) {
		String jpql = " select shop from Shop shop where shop.treePath like :treePath and shop.isList = true and shop.isTop = true and shop.city like :city order by shop.totalScore / shop.scoreTimes desc ";
		TypedQuery<Shop> query = entityManager.createQuery(jpql, Shop.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%," + category.getId() + ",%").setParameter("city", "%" + city + "%").setMaxResults(10).getResultList();
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
