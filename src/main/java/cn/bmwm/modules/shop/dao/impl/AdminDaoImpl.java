/*


 * */
package cn.bmwm.modules.shop.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;


import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.AdminDao;
import cn.bmwm.modules.shop.entity.Admin;

/**
 * Dao - 管理员
 * 
 *
 * @version 1.0
 */
@Repository("adminDaoImpl")
public class AdminDaoImpl extends BaseDaoImpl<Admin, Long> implements AdminDao {

	public boolean usernameExists(String username) {
		if (username == null) {
			return false;
		}
		String jpql = "select count(*) from Admin admin where lower(admin.username) = lower(:username)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		return count > 0;
	}

	public Admin findByUsername(String username) {
		if (username == null) {
			return null;
		}
		try {
			String jpql = "select admin from Admin admin where lower(admin.username) = lower(:username)";
			return entityManager.createQuery(jpql, Admin.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}