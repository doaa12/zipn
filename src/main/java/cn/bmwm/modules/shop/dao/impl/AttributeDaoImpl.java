/*


 * */
package cn.bmwm.modules.shop.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.dao.AttributeDao;
import cn.bmwm.modules.shop.entity.Attribute;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.Shop;

/**
 * Dao - 属性
 * 
 *
 * @version 1.0
 */
@Repository("attributeDaoImpl")
public class AttributeDaoImpl extends BaseDaoImpl<Attribute, Long> implements AttributeDao {
	
	/**
	 * 查找店铺分类属性
	 * @param shop
	 * @param pageable
	 * @return
	 */
	public Page<Attribute> findPage(Shop shop, Pageable pageable){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Attribute> criteriaQuery = criteriaBuilder.createQuery(Attribute.class);
		Root<Attribute> root = criteriaQuery.from(Attribute.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("shop")).value(shop));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
		return super.findPage(criteriaQuery, pageable);
	}

	/**
	 * 设置propertyIndex并保存
	 * 
	 * @param attribute
	 *            属性
	 */
	@Override
	public void persist(Attribute attribute) {
		Assert.notNull(attribute);
		String jpql = "select attribute.propertyIndex from Attribute attribute where attribute.shopCategory = :shopCategory";
		List<Integer> propertyIndexs = entityManager.createQuery(jpql, Integer.class).setFlushMode(FlushModeType.COMMIT).setParameter("shopCategory", attribute.getShopCategory()).getResultList();
		for (int i = 0; i < Product.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			if (!propertyIndexs.contains(i)) {
				attribute.setPropertyIndex(i);
				super.persist(attribute);
				break;
			}
		}
	}

	/**
	 * 清除商品属性值并删除
	 * 
	 * @param attribute
	 *            属性
	 */
	@Override
	public void remove(Attribute attribute) {
		if (attribute != null) {
			String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + attribute.getPropertyIndex();
			String jpql = "update Product product set product." + propertyName + " = null where product.shopCategory = :shopCategory";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("shopCategory", attribute.getShopCategory()).executeUpdate();
			super.remove(attribute);
		}
	}

}