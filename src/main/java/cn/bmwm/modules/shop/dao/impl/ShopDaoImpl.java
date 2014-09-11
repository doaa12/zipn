/**
 * 
 */
package cn.bmwm.modules.shop.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.controller.app.vo.ItemPage;
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
	 * @param order : 排序，1：推荐，2：人气，3：距离，4：价格
	 * @param x : 经度
	 * @param y : 纬度
	 * @return
	 */
	public ItemPage<Shop> findList(String city, ProductCategory category, Integer page, Integer size, Integer order, BigDecimal x, BigDecimal y) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Shop> cq = cb.createQuery(Shop.class);
		Root<Shop> root = cq.from(Shop.class);
		cq.select(root);
		
		Predicate restrictions = cb.conjunction();
		
		restrictions = cb.and(restrictions, cb.equal(root.get("isList"), true));
		restrictions = cb.and(restrictions, cb.like(root.<String>get("city"), "%" + city + "%"));
		restrictions = cb.and(restrictions, cb.like(root.<String>get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + category.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%"));
		
		cq.where(restrictions);
		
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		
		if(order == 1) {
			orderList.add(cb.desc(root.get("isTop")));
			orderList.add(cb.desc(root.get("createDate")));
		}else if(order == 2) {
			orderList.add(cb.desc(root.get("scoreTimes")));
			orderList.add(cb.asc(root.get("createDate")));
		}else if(order == 3) {
			Expression<BigDecimal> longitude = root.get("longitude");
			Expression<BigDecimal> latitude = root.get("latitude");
			Expression<BigDecimal> longitudeDiff = cb.diff(longitude, x);
			Expression<BigDecimal> latitudeDiff = cb.diff(latitude, y);
			orderList.add(cb.asc(cb.sum(cb.prod(longitudeDiff, longitudeDiff), cb.prod(latitudeDiff, latitudeDiff))));
		}else if(order == 4) {
			orderList.add(cb.asc(root.get("avgPrice")));
			orderList.add(cb.desc(root.get("createDate")));
		}
		
		cq.orderBy(orderList);
		
		int start = (page - 1) * size;
		TypedQuery<Shop> query = entityManager.createQuery(cq).setFlushMode(FlushModeType.COMMIT);
		query.setFirstResult(start);
		query.setMaxResults(size);
		
		List<Shop> list = query.getResultList();
		
		ItemPage<Shop> itemPage = new ItemPage<Shop>();
		
		itemPage.setPage(page);
		itemPage.setSize(size);
		itemPage.setList(list);
		
		
		return itemPage;
		
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
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%," + category.getId() + ",%").setParameter("city", "%" + city + "%").setFirstResult(0).setMaxResults(10).getResultList();
	}
	
	/**
	 * 查找收藏数量前几名的店铺
	 * @param city
	 * @return
	 */
	@Cacheable(value = "shop", key = "'city' + #city + 'findFavoriteTopShopList'")
	public List<Shop> findFavoriteTopShopList(String city) {
		String jpql = " select shop from Shop shop where shop.city like :city and shop.isList = true order by shop.favoriteCount desc,createDate ";
		TypedQuery<Shop> query = entityManager.createQuery(jpql, Shop.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("city", "%" + city + "%").setFirstResult(0).setMaxResults(5).getResultList();
	}
	
	/**
	 * 查询虚拟分类店铺
	 * @param category
	 * @return
	 */
	public List<Shop> findVirtualCategoryShopList(Long catId) {
		String jpql = " select shop from Shop shop join shop.virtualCategories category where category.id = :id order by shop.isTop desc,shop.favoriteCount desc ";
		TypedQuery<Shop> query = entityManager.createQuery(jpql, Shop.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("id", catId).setMaxResults(10).getResultList();
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
