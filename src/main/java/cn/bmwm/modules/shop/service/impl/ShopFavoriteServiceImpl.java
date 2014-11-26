/**
 * 
 */
package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bmwm.modules.shop.dao.ShopDao;
import cn.bmwm.modules.shop.dao.ShopFavoriteDao;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopFavorite;
import cn.bmwm.modules.shop.service.ShopFavoriteService;

/**
 * 店铺收藏
 * @author zhoupuyue
 * @date 2014-8-26
 */
@Service("shopFavoriteServiceImpl")
public class ShopFavoriteServiceImpl extends BaseServiceImpl<ShopFavorite,Long> implements ShopFavoriteService {
	
	@Resource(name = "shopFavoriteDaoImpl")
	private ShopFavoriteDao shopFavoriteDao;
	
	@Resource(name = "shopDaoImpl")
	private ShopDao shopDao;
	
	@Resource(name = "shopFavoriteDaoImpl")
	public void setBaseDao(ShopFavoriteDao shopFavoriteDao) {
		super.setBaseDao(shopFavoriteDao);
	}

	/**
	 * 查询收藏店铺动态
	 * @param member
	 * @return
	 */
	public List<Shop> findDynamicShops(Member member){
		return shopFavoriteDao.findDynamicShops(member);
	}
	
	/**
	 * 判断用户是否收藏了该店铺
	 * @param member
	 * @param shop
	 * @return
	 */
	public boolean isUserCollectShop(Member member, Shop shop) {
		ShopFavorite favorite = shopFavoriteDao.findShopFavoriteByMemberShop(member, shop);
		return favorite != null;
	}
	
	/**
	 * 查询店铺收藏
	 * @param member
	 * @param shop
	 * @return
	 */
	public ShopFavorite findShopFavorite(Member member, Shop shop) {
		return shopFavoriteDao.findShopFavoriteByMemberShop(member, shop);
	}
	
	/**
	 * 收藏店铺
	 * @param favorite
	 * @param shop
	 */
	@Transactional
	@CacheEvict(value = {"shop"}, allEntries = true)
	public void collectShop(ShopFavorite favorite, Shop shop) {
		
		super.save(favorite);
		
		Long count = shop.getFavoriteCount();
		
		if(count == null) {
			shop.setFavoriteCount(1L);
		}else{
			shop.setFavoriteCount(count + 1);
		}
		
		shopDao.merge(shop);
		
	}
	
	/**
	 * 查询收藏店铺
	 * @param member
	 * @return
	 */
	public List<Shop> findFavoriteShopList(Member member) {
		return shopFavoriteDao.findFavoriteShopList(member);
	}
	
}
