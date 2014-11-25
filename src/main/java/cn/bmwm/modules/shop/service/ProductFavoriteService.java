package cn.bmwm.modules.shop.service;

import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductFavorite;

/**
 * 商品收藏
 * @author zby
 * 2014-11-22 上午8:29:21
 */
public interface ProductFavoriteService extends BaseService<ProductFavorite, Long> {
	
	/**
	 * 判断用户是否收藏了该商品
	 * @param member
	 * @param shop
	 * @return
	 */
	boolean isUserCollectProduct(Member member, Product product);
	
	/**
	 * 获取用户收藏的商品
	 * @param member
	 * @param product
	 * @return
	 */
	ProductFavorite findProductFavorite(Member member, Product product);

}
