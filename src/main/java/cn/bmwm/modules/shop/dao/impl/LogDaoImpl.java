/*


 * */
package cn.bmwm.modules.shop.dao.impl;

import javax.persistence.FlushModeType;


import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.LogDao;
import cn.bmwm.modules.shop.entity.Log;

/**
 * Dao - 日志
 * 
 *
 * @version 1.0
 */
@Repository("logDaoImpl")
public class LogDaoImpl extends BaseDaoImpl<Log, Long> implements LogDao {

	public void removeAll() {
		String jpql = "delete from Log log";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
	}

}