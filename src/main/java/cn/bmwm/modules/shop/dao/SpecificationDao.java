/*


 * */
package cn.bmwm.modules.shop.dao;

import java.util.List;

import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.Specification;

/**
 * Dao - 规格
 * 
 *
 * @version 1.0
 */
public interface SpecificationDao extends BaseDao<Specification, Long> {
	
	/**
	 * 查询店铺商品规格
	 * @param shop
	 * @return
	 */
	List<Specification> findShopSpecificationList(Shop shop);
	
}