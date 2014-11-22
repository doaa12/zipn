/**
 * 
 */
package cn.bmwm.modules.shop.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.ShopFavoriteDao;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopFavorite;

/**
 * 店铺收藏
 * @author zhoupuyue
 * @date 2014-8-26
 */
@Repository("shopFavoriteDaoImpl")
public class ShopFavoriteDaoImpl extends BaseDaoImpl<ShopFavorite,Long> implements ShopFavoriteDao {
	
	/**
	 * 查询收藏店铺动态
	 * @param member
	 * @return
	 */
	public List<Shop> findDynamicShops(Member member) {
		String jpql = "select shopFavorite.shop from ShopFavorite shopFavorite where shopFavorite.member = :member order by shopFavorite.shop.favoriteCount desc ";
		return entityManager.createQuery(jpql, Shop.class).setFlushMode(FlushModeType.COMMIT).setParameter("member", member).setMaxResults(10).getResultList();
	}
	
}
