/*


 * */
package cn.bmwm.modules.shop.dao;

import java.util.List;

import cn.bmwm.modules.shop.entity.Tag;
import cn.bmwm.modules.shop.entity.Tag.Type;


/**
 * Dao - 标签
 * 
 *
 * @version 1.0
 */
public interface TagDao extends BaseDao<Tag, Long> {

	/**
	 * 查找标签
	 * 
	 * @param type
	 *            类型
	 * @return 标签
	 */
	List<Tag> findList(Type type);

}