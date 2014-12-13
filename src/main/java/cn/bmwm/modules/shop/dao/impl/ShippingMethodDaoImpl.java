/*


 * */
package cn.bmwm.modules.shop.dao.impl;


import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.ShippingMethodDao;
import cn.bmwm.modules.shop.entity.ShippingMethod;
import cn.bmwm.modules.shop.entity.Shop;

/**
 * Dao - 配送方式
 * 
 *
 * @version 1.0
 */
@Repository("shippingMethodDaoImpl")
public class ShippingMethodDaoImpl extends BaseDaoImpl<ShippingMethod, Long> implements ShippingMethodDao {
	
	/**
	 * 按店铺查询运送方式设置
	 * @param shop
	 * @return
	 */
	public ShippingMethod findByShop(Shop shop) {
		String jpql = "select shippingMethod from ShippingMethod shippingMethod where shippingMethod.shop = :shop ";
		try{
			return entityManager.createQuery(jpql, ShippingMethod.class).setFlushMode(FlushModeType.COMMIT).setParameter("shop", shop).getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}
	
}