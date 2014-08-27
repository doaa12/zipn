/**
 * 
 */
package cn.bmwm.modules.shop.dao;

import java.util.List;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;

/**
 * Dao - 店铺
 * @author zhoupuyue
 * @date 2014-8-22
 */
public interface ShopDao extends BaseDao<Shop,Long> {

	/**
	 * 查找店铺分页
	 * @param productCategory
	 * @param city
	 * @param pageable
	 * @return
	 */
	Page<Shop> findPage(ProductCategory productCategory, String city, Boolean isTop, Boolean isList, Pageable pageable);
	
	/**
	 * 查找推荐店铺
	 * @param category
	 * @param city
	 * @return
	 */
	List<Shop> findRecommendList(String city, ProductCategory category);
	
	/**
	 * 获取所有开通的城市
	 * @return
	 */
	List<String> findAllCities();
	
}
