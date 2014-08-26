/**
 * 
 */
package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
	
}
