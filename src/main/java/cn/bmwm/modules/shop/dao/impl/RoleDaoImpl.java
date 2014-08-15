/*


 * */
package cn.bmwm.modules.shop.dao.impl;


import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.RoleDao;
import cn.bmwm.modules.shop.entity.Role;

/**
 * Dao - 角色
 * 
 *
 * @version 1.0
 */
@Repository("roleDaoImpl")
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {

}