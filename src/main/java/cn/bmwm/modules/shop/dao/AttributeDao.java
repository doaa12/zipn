/*


 * */
package cn.bmwm.modules.shop.dao;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.Attribute;
import cn.bmwm.modules.shop.entity.Shop;

/**
 * Dao - 属性
 * 
 *
 * @version 1.0
 */
public interface AttributeDao extends BaseDao<Attribute, Long> {

	/**
	 * 查找店铺分类属性
	 * @param shop
	 * @param pageable
	 * @return
	 */
	Page<Attribute> findPage(Shop shop, Pageable pageable);
	
}