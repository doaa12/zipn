/*


 * */
package cn.bmwm.modules.shop.dao;

import java.util.List;

import cn.bmwm.modules.shop.entity.Navigation;
import cn.bmwm.modules.shop.entity.Navigation.Position;


/**
 * Dao - 导航
 * 
 *
 * @version 1.0
 */
public interface NavigationDao extends BaseDao<Navigation, Long> {

	/**
	 * 查找导航
	 * 
	 * @param position
	 *            位置
	 * @return 导航
	 */
	List<Navigation> findList(Position position);

}