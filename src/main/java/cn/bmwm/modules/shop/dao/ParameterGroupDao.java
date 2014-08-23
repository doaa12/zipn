/*


 * */
package cn.bmwm.modules.shop.dao;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.ParameterGroup;
import cn.bmwm.modules.shop.entity.Shop;

/**
 * Dao - 参数组
 * 
 *
 * @version 1.0
 */
public interface ParameterGroupDao extends BaseDao<ParameterGroup, Long> {

	/**
	 * 查找店铺参数组分页
	 * @param shop
	 * @param pageable
	 * @return
	 */
	Page<ParameterGroup> findPage(Shop shop, Pageable pageable);
	
}