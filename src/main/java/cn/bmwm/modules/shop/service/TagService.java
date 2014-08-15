/*


 * */
package cn.bmwm.modules.shop.service;

import java.util.List;

import cn.bmwm.common.persistence.Filter;
import cn.bmwm.common.persistence.Order;
import cn.bmwm.modules.shop.entity.Tag;
import cn.bmwm.modules.shop.entity.Tag.Type;


/**
 * Service - 标签
 * 
 *
 * @version 1.0
 */
public interface TagService extends BaseService<Tag, Long> {

	/**
	 * 查找标签
	 * 
	 * @param type
	 *            类型
	 * @return 标签
	 */
	List<Tag> findList(Type type);

	/**
	 * 查找标签(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param cacheRegion
	 *            缓存区域
	 * @return 标签(缓存)
	 */
	List<Tag> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion);

}