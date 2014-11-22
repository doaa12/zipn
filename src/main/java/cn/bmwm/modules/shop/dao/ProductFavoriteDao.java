package cn.bmwm.modules.shop.dao;

import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductFavorite;

/**
 * 商品收藏
 * @author zby
 * 2014-11-22 上午8:31:16
 */
public interface ProductFavoriteDao extends BaseDao<ProductFavorite,Long>{
	
	/**
	 * 查询用户收藏的商品
	 * @param member
	 * @param product
	 * @return
	 */
	ProductFavorite findProductFavoriteByMemberProduct(Member member, Product product);

}
