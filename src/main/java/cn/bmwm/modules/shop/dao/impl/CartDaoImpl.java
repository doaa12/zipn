/*


 * */
package cn.bmwm.modules.shop.dao.impl;

import java.util.Date;

import javax.persistence.FlushModeType;


import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.CartDao;
import cn.bmwm.modules.shop.entity.Cart;

/**
 * Dao - 购物车
 * 
 *
 * @version 1.0
 */
@Repository("cartDaoImpl")
public class CartDaoImpl extends BaseDaoImpl<Cart, Long> implements CartDao {

	public void evictExpired() {
		String jpql = "delete from Cart cart where cart.modifyDate <= :expire";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("expire", DateUtils.addSeconds(new Date(), -Cart.TIMEOUT)).executeUpdate();
	}

}