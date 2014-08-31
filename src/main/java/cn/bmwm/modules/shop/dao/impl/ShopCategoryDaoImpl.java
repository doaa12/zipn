/**
 * 
 */
package cn.bmwm.modules.shop.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.ShopCategoryDao;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopCategory;

/**
 * Dao - 店铺分类
 * @author zhoupuyue
 * @date 2014-8-20
 */
@Repository("shopCategoryDaoImpl")
public class ShopCategoryDaoImpl extends BaseDaoImpl<ShopCategory,Long> implements ShopCategoryDao {
	
	/**
	 * 获取店铺所有分类
	 * @return
	 */
	public List<ShopCategory> findAllShopCategories(Shop shop) {
		String jpql = "select category from ShopCategory category where shop = :shop order by orders ";
		TypedQuery<ShopCategory> query = entityManager.createQuery(jpql, ShopCategory.class);
		return query.setFlushMode(FlushModeType.COMMIT).setParameter("shop", shop).getResultList();
	}
	
}
