/*


 * */
package cn.bmwm.modules.shop.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.bmwm.common.persistence.Filter;
import cn.bmwm.common.persistence.Order;
import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.controller.app.vo.ItemPage;
import cn.bmwm.modules.shop.dao.GoodsDao;
import cn.bmwm.modules.shop.dao.ProductDao;
import cn.bmwm.modules.shop.dao.SnDao;
import cn.bmwm.modules.shop.entity.Attribute;
import cn.bmwm.modules.shop.entity.Brand;
import cn.bmwm.modules.shop.entity.Goods;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Order.OrderStatus;
import cn.bmwm.modules.shop.entity.Order.PaymentStatus;
import cn.bmwm.modules.shop.entity.OrderItem;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.Product.OrderType;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Promotion;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopCategory;
import cn.bmwm.modules.shop.entity.Sn.Type;
import cn.bmwm.modules.shop.entity.SpecificationValue;
import cn.bmwm.modules.shop.entity.Tag;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * Dao - 商品
 * 
 *
 * @version 1.0
 */
@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements ProductDao {

	private static final Pattern pattern = Pattern.compile("\\d*");

	@Resource(name = "goodsDaoImpl")
	private GoodsDao goodsDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	public boolean snExists(String sn) {
		if (sn == null) {
			return false;
		}
		String jpql = "select count(*) from Product product where lower(product.sn) = lower(:sn)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
		return count > 0;
	}

	public Product findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select product from Product product where lower(product.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Product.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * 查找推荐商品
	 * @return
	 */
	public List<Product> findRecommendList(String city, ProductCategory category) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("city"), "%" + city + "%"));
		
		if(category != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + category.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%"));
		}
		
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
		return super.findList(criteriaQuery, 0, 10, null, null);
		
	}
	
	/**
	 * 查找店铺推荐商品
	 * @param shop ： 店铺
	 * @return
	 */
	public List<Product> findShopRecommendList(Shop shop) {
		String jpql = " select product from Product product where product.shop = :shop and product.isMarketable = true and product.isGift = false and product.isTop = true ";
		TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("shop", shop).setFirstResult(0).setMaxResults(10).getResultList();
	}
	
	/**
	 * 查找热销商品
	 * @return
	 */
	public List<Product> findHotList(String city, ProductCategory category) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("city"), "%" + city + "%"));
		
		if(category != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + category.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%"));
		}
		
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")));
		
		return super.findList(criteriaQuery, 0, 10, null, null);
		
	}
	
	/**
	 * 商品列表
	 * @param city : 城市
	 * @param category : 分类
	 * @param page : 页码
	 * @param size : 每页记录数
	 * @param order : 排序方式，1：促销，2：新品，3：销量，4：推荐
	 * @return
	 */
	public ItemPage<Product> findList(String city, ProductCategory category, Integer page, Integer size, Integer order) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> root = cq.from(Product.class);
		cq.select(root);
		
		Predicate restrictions = cb.conjunction();
		
		restrictions = cb.and(restrictions, cb.equal(root.get("isMarketable"), true));
		restrictions = cb.and(restrictions, cb.equal(root.get("isGift"), false));
		restrictions = cb.and(restrictions, cb.like(root.<String>get("city"), "%" + city + "%"));
		restrictions = cb.and(restrictions, cb.like(root.<String>get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + category.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%"));
		
		cq.where(restrictions);
		
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		
		if(order == 1) {
			orderList.add(cb.desc(root.get("isPromotion")));
			orderList.add(cb.desc(root.get("sales")));
		}else if(order == 2) {
			orderList.add(cb.desc(root.get("createDate")));
			orderList.add(cb.desc(root.get("sales")));
		}else if(order == 3) {
			orderList.add(cb.desc(root.get("sales")));
			orderList.add(cb.desc(root.get("createDate")));
		}else if(order == 4) {
			orderList.add(cb.desc(root.get("isTop")));
			orderList.add(cb.desc(root.get("createDate")));
		}
		
		cq.orderBy(orderList);
		
		int start = (page - 1) * size;
		TypedQuery<Product> query = entityManager.createQuery(cq).setFlushMode(FlushModeType.COMMIT);
		query.setFirstResult(start);
		query.setMaxResults(size);
		
		List<Product> list = query.getResultList();
		
		ItemPage<Product> result = new ItemPage<Product>();
		
		result.setPage(page);
		result.setSize(size);
		result.setList(list);
		
		return result;
		
	}
	
	/**
	 * 店铺商品列表
	 * @param shopId：店铺ID
	 * @param type：列表类型，1：所有商品，2：促销商品，3：店铺分类
	 * @param catId：店铺分类
	 * @param page：页码
	 * @param size：每页显示商品数量
	 * @param order：排序方式，1：推荐，2：人气，3：距离，4：价格
	 * @param x：纬度
	 * @param y：经度
	 * @return
	 */
	public ItemPage<Product> findShopProductList(Shop shop, Integer type, ShopCategory category, Integer page, Integer size, Integer order, Integer x, Integer y) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> root = cq.from(Product.class);
		cq.select(root);
		
		Predicate restrictions = cb.conjunction();
		
		restrictions = cb.and(restrictions, cb.equal(root.get("isMarketable"), true));
		restrictions = cb.and(restrictions, cb.equal(root.get("isGift"), false));
		restrictions = cb.and(restrictions, cb.equal(root.get("shop"), shop));
		
		if(type == 2) {
			restrictions = cb.and(restrictions, cb.equal(root.get("isPromotion"), true));
		}else if(type == 3) {
			restrictions = cb.and(restrictions, cb.equal(root.get("shopCategory"), category));
		}
		
		cq.where(restrictions);
		
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		
		if(order == 1) {
			orderList.add(cb.desc(root.get("isTop")));
			orderList.add(cb.desc(root.get("sales")));
		}else if(order == 2) {
			orderList.add(cb.desc(root.get("sales")));
			orderList.add(cb.asc(root.get("createDate")));
		}else if(order == 3) {
			orderList.add(cb.asc(root.get("createDate")));
		}else if(order == 4) {
			orderList.add(cb.asc(root.get("price")));
			orderList.add(cb.asc(root.get("createDate")));
		}
		
		cq.orderBy(orderList);
		
		TypedQuery<Product> query = entityManager.createQuery(cq).setFlushMode(FlushModeType.COMMIT);
		
		int start = (page - 1) * size;
		query.setFirstResult(start);
		query.setMaxResults(size);
		
		List<Product> list = query.getResultList();
		
		ItemPage<Product> result = new ItemPage<Product>();
		
		result.setPage(page);
		result.setSize(size);
		result.setList(list);
		
		return result;
		
	}
	
	/**
	 * 查询店铺商品数量
	 * @param shop
	 * @return
	 */
	public Long findShopProductCount(Shop shop) {
		String jpql = " select count(*) from Product product where product.shop = :shop and product.isList = true and product.isGift = false ";
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("shop", shop).getSingleResult();
	}

	public List<Product> search(String keyword, Boolean isGift, Integer count) {
		if (StringUtils.isEmpty(keyword)) {
			return Collections.<Product> emptyList();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (pattern.matcher(keyword).matches()) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("id"), Long.valueOf(keyword)), criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"), criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		} else {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"), criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), isGift));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findList(criteriaQuery, null, count, null, null);
	}

	public List<Product> findList(ProductCategory productCategory, Brand brand, Promotion promotion, List<Tag> tags, Map<Attribute, String> attributeValue, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert, OrderType orderType, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		/*
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		*/
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
		}
		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root), criteriaBuilder.equal(subqueryRoot1.join("promotions"), promotion));
			
			/*
			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root), criteriaBuilder.equal(subqueryRoot2.join("productCategory").join("promotions"), promotion));
			*/
			
			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root), criteriaBuilder.equal(subqueryRoot3.join("brand").join("promotions"), promotion));

			//restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.exists(subquery1), criteriaBuilder.exists(subquery2), criteriaBuilder.exists(subquery3)));
		
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.exists(subquery1), criteriaBuilder.exists(subquery3)));
			
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root), subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
			}
		}
		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), isGift));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public List<Product> findList(ProductCategory productCategory, Date beginDate, Date endDate, Integer first, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), true));
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findList(criteriaQuery, first, count, null, null);
	}

	public List<Product> findList(Goods goods, Set<Product> excludes) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (goods != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("goods"), goods));
		}
		if (excludes != null && !excludes.isEmpty()) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(root.in(excludes)));
		}
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

	public List<Object[]> findSalesList(Date beginDate, Date endDate, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Product> product = criteriaQuery.from(Product.class);
		Join<Product, OrderItem> orderItems = product.join("orderItems");
		Join<Product, cn.bmwm.modules.shop.entity.Order> order = orderItems.join("order");
		criteriaQuery.multiselect(product.get("id"), product.get("sn"), product.get("name"), product.get("fullName"), product.get("price"), criteriaBuilder.sum(orderItems.<Integer> get("quantity")), criteriaBuilder.sum(criteriaBuilder.prod(orderItems.<Integer> get("quantity"), orderItems.<BigDecimal> get("price"))));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(order.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(order.<Date> get("createDate"), endDate));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(order.get("orderStatus"), OrderStatus.completed), criteriaBuilder.equal(order.get("paymentStatus"), PaymentStatus.paid));
		criteriaQuery.where(restrictions);
		criteriaQuery.groupBy(product.get("id"), product.get("sn"), product.get("name"), product.get("fullName"), product.get("price"));
		criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(criteriaBuilder.prod(orderItems.<Integer> get("quantity"), orderItems.<BigDecimal> get("price")))));
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		if (count != null && count >= 0) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public Page<Product> findPage(ProductCategory productCategory, Brand brand, Promotion promotion, List<Tag> tags, Map<Attribute, String> attributeValue, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert, OrderType orderType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
		}
		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root), criteriaBuilder.equal(subqueryRoot1.join("promotions"), promotion));

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root), criteriaBuilder.equal(subqueryRoot2.join("productCategory").join("promotions"), promotion));

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root), criteriaBuilder.equal(subqueryRoot3.join("brand").join("promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.exists(subquery1), criteriaBuilder.exists(subquery2), criteriaBuilder.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root), subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
			}
		}
		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), isGift));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}
	
	//zhoupuyue
	public Page<Product> findPage(Shop shop, ShopCategory shopCategory, Brand brand, Promotion promotion, List<Tag> tags, Map<Attribute, String> attributeValue, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert, OrderType orderType, Pageable pageable) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("shop"), shop));
		
		if (shopCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("shopCategory"), shopCategory));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
		}
		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root), criteriaBuilder.equal(subqueryRoot1.join("promotions"), promotion));

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root), criteriaBuilder.equal(subqueryRoot2.join("productCategory").join("promotions"), promotion));

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root), criteriaBuilder.equal(subqueryRoot3.join("brand").join("promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.exists(subquery1), criteriaBuilder.exists(subquery2), criteriaBuilder.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root), subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
			}
		}
		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), isGift));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Product> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return new Page<Product>(Collections.<Product> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.join("favoriteMembers"), member));
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Member favoriteMember, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (favoriteMember != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("favoriteMembers"), favoriteMember));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), isGift));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public boolean isPurchased(Member member, Product product) {
		if (member == null || product == null) {
			return false;
		}
		String jqpl = "select count(*) from OrderItem orderItem where orderItem.product = :product and orderItem.order.member = :member and orderItem.order.orderStatus = :orderStatus";
		Long count = entityManager.createQuery(jqpl, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("product", product).setParameter("member", member).setParameter("orderStatus", OrderStatus.completed).getSingleResult();
		return count > 0;
	}

	/**
	 * 设置值并保存
	 * 
	 * @param product
	 *            商品
	 */
	@Override
	public void persist(Product product) {
		Assert.notNull(product);

		setValue(product);
		super.persist(product);
	}

	/**
	 * 设置值并更新
	 * 
	 * @param product
	 *            商品
	 * @return 商品
	 */
	@Override
	public Product merge(Product product) {
		Assert.notNull(product);

		if (!product.getIsGift()) {
			String jpql = "delete from GiftItem giftItem where giftItem.gift = :product";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("product", product).executeUpdate();
		}
		if (!product.getIsMarketable() || product.getIsGift()) {
			String jpql = "delete from CartItem cartItem where cartItem.product = :product";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("product", product).executeUpdate();
		}
		setValue(product);
		return super.merge(product);
	}

	@Override
	public void remove(Product product) {
		if (product != null) {
			Goods goods = product.getGoods();
			if (goods != null && goods.getProducts() != null) {
				goods.getProducts().remove(product);
				if (goods.getProducts().isEmpty()) {
					goodsDao.remove(goods);
				}
			}
		}
		super.remove(product);
	}

	/**
	 * 设置值
	 * 
	 * @param product
	 *            商品
	 */
	private void setValue(Product product) {
		if (product == null) {
			return;
		}
		if (StringUtils.isEmpty(product.getSn())) {
			String sn;
			do {
				sn = snDao.generate(Type.product);
			} while (snExists(sn));
			product.setSn(sn);
		}
		StringBuffer fullName = new StringBuffer(product.getName());
		if (product.getSpecificationValues() != null && !product.getSpecificationValues().isEmpty()) {
			List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>(product.getSpecificationValues());
			Collections.sort(specificationValues, new Comparator<SpecificationValue>() {
				public int compare(SpecificationValue a1, SpecificationValue a2) {
					return new CompareToBuilder().append(a1.getSpecification(), a2.getSpecification()).toComparison();
				}
			});
			fullName.append(Product.FULL_NAME_SPECIFICATION_PREFIX);
			int i = 0;
			for (Iterator<SpecificationValue> iterator = specificationValues.iterator(); iterator.hasNext(); i++) {
				if (i != 0) {
					fullName.append(Product.FULL_NAME_SPECIFICATION_SEPARATOR);
				}
				fullName.append(iterator.next().getName());
			}
			fullName.append(Product.FULL_NAME_SPECIFICATION_SUFFIX);
		}
		product.setFullName(fullName.toString());
	}

}