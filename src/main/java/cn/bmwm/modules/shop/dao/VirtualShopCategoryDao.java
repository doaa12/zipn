/**
 * 
 */
package cn.bmwm.modules.shop.dao;

import java.util.List;

import cn.bmwm.modules.shop.entity.VirtualShopCategory;

/**
 * Dao -- 虚拟店铺分类
 * @author zhoupuyue
 * @date 2014-9-11
 */
public interface VirtualShopCategoryDao extends BaseDao<VirtualShopCategory,Long> {

	/**
	 * 查询虚拟店铺分类
	 * @param city
	 * @return
	 */
	List<VirtualShopCategory> findList(String city);
	
}
