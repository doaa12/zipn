/**
 * 
 */
package cn.bmwm.modules.shop.service;

import java.util.List;

import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopFavorite;

/**
 * 店铺收藏
 * @author zhoupuyue
 * @date 2014-8-26
 */
public interface ShopFavoriteService extends BaseService<ShopFavorite,Long> {

	/**
	 * 查询收藏店铺动态
	 * @param member
	 * @return
	 */
	List<Shop> findDynamicShops(Member member);
	
}
