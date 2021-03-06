/**
 * 
 */
package cn.bmwm.modules.shop.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

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
		String jpql = "select favorite.shop from ShopFavorite favorite where favorite.member = :member order by favorite.shop.favoriteCount desc ";
		return entityManager.createQuery(jpql, Shop.class).setFlushMode(FlushModeType.COMMIT).setParameter("member", member).setMaxResults(10).getResultList();
	}
	
	/**
	 * 查询用户收藏的店铺
	 * @param member
	 * @param shop
	 * @return
	 */
	public ShopFavorite findShopFavoriteByMemberShop(Member member, Shop shop) {
		try{
			String jpql = "select favorite from ShopFavorite favorite where favorite.member = :member and favorite.shop = :shop ";
			return entityManager.createQuery(jpql, ShopFavorite.class).setFlushMode(FlushModeType.COMMIT).setParameter("member", member).setParameter("shop", shop).getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
		
	}
	
	/**
	 * 查询收藏店铺
	 * @param member
	 * @return
	 */
	public List<Shop> findFavoriteShopList(Member member) {
		String jpql = " select favorite.shop from ShopFavorite favorite where favorite.member = :member and  favorite.shop.isList = true ";
		return entityManager.createQuery(jpql, Shop.class).setFlushMode(FlushModeType.COMMIT).setParameter("member", member).getResultList(); 
	}
	
}
