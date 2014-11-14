/*


 * */
package cn.bmwm.modules.shop.service;

import java.util.List;

import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.Specification;

/**
 * Service - 规格
 * 
 *
 * @version 1.0
 */
public interface SpecificationService extends BaseService<Specification, Long> {
	
	/**
	 * 查询店铺商品规格
	 * @param shop
	 * @return
	 */
	List<Specification> findShopSpecificationList(Shop shop);

}