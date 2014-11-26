/**
 * 
 */
package cn.bmwm.modules.shop.dao;

import java.util.List;

import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopFavorite;

/**
 * 店铺收藏
 * @author zhoupuyue
 * @date 2014-8-26
 */
public interface ShopFavoriteDao extends BaseDao<ShopFavorite,Long> {

	/**
	 * 查询收藏店铺动态
	 * @param member
	 * @return
	 */
	List<Shop> findDynamicShops(Member member);
	
	/**
	 * 查询用户收藏的店铺
	 * @param member
	 * @param shop
	 * @return
	 */
	ShopFavorite findShopFavoriteByMemberShop(Member member, Shop shop);
	
	/**
	 * 查询收藏店铺
	 * @param member
	 * @return
	 */
	List<Shop> findFavoriteShopList(Member member);
	
}
