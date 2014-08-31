/**
 * 
 */
package cn.bmwm.modules.shop.dao;

import java.util.List;

import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopCategory;

/**
 * Dao - 店铺分类
 * @author zhoupuyue
 * @date 2014-8-20
 */
public interface ShopCategoryDao extends BaseDao<ShopCategory,Long> {

	/**
	 * 获取店铺所有分类
	 * @return
	 */
	List<ShopCategory> findAllShopCategories(Shop shop);
	
}
