/*


 * */
package cn.bmwm.modules.shop.dao;

import java.util.List;

import cn.bmwm.modules.shop.entity.Admin;

/**
 * Dao - 管理员
 * 
 *
 * @version 1.0
 */
public interface AdminDao extends BaseDao<Admin, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找管理员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	Admin findByUsername(String username);
	
	/**
	 * 查找没有分配店铺的管理员
	 * @return
	 */
	List<Admin> findFreeAdmins();

}