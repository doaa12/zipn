/**
 * 
 */
package cn.bmwm.modules.shop.service;

import java.util.List;

import cn.bmwm.modules.shop.entity.VirtualShopCategory;

/**
 * Service -- 虚拟店铺分类
 * @author zhoupuyue
 * @date 2014-9-11
 */
public interface VirtualShopCategoryService extends BaseService<VirtualShopCategory,Long> {

	/**
	 * 查询虚拟店铺分类
	 * @param city
	 * @return
	 */
	List<VirtualShopCategory> findList(String city);
	
	/**
	 * 后台管理查询虚拟店铺分类
	 * @param city
	 * @return
	 */
	List<VirtualShopCategory> adminFindList(String city);
	
}
