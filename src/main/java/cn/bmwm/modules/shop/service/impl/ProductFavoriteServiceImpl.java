package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.ProductFavoriteDao;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductFavorite;
import cn.bmwm.modules.shop.service.ProductFavoriteService;

/**
 * 商品收藏
 * @author zby
 * 2014-11-22 上午8:34:15
 */
@Service("productFavoriteServiceImpl")
public class ProductFavoriteServiceImpl extends BaseServiceImpl<ProductFavorite,Long> implements ProductFavoriteService {
	
	@Resource(name = "productFavoriteDaoImpl")
	private ProductFavoriteDao productFavoriteDao;
	
	@Resource(name = "productFavoriteDaoImpl")
	public void setBaseDao(ProductFavoriteDao productFavoriteDao) {
		super.setBaseDao(productFavoriteDao);
	}
	
	/**
	 * 判断用户是否收藏了该商品
	 * @param member
	 * @param shop
	 * @return
	 */
	public boolean isUserCollectProduct(Member member, Product product) {
		ProductFavorite favorite = productFavoriteDao.findProductFavoriteByMemberProduct(member, product);
		return favorite != null;
	}
	
}
