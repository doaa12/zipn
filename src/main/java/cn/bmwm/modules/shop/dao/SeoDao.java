/*


 * */
package cn.bmwm.modules.shop.dao;

import cn.bmwm.modules.shop.entity.Seo;
import cn.bmwm.modules.shop.entity.Seo.Type;

/**
 * Dao - SEO设置
 * 
 *
 * @version 1.0
 */
public interface SeoDao extends BaseDao<Seo, Long> {

	/**
	 * 查找SEO设置
	 * 
	 * @param type
	 *            类型
	 * @return SEO设置
	 */
	Seo find(Type type);

}