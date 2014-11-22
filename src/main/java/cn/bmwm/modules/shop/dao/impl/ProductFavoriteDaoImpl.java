package cn.bmwm.modules.shop.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.ProductFavoriteDao;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductFavorite;

/**
 * 商品收藏
 * @author zby
 * 2014-11-22 上午8:30:56
 */
@Repository("productFavoriteDaoImpl")
public class ProductFavoriteDaoImpl extends BaseDaoImpl<ProductFavorite,Long> implements ProductFavoriteDao {

	/**
	 * 查询用户收藏的商品
	 * @param member
	 * @param product
	 * @return
	 */
	public ProductFavorite findProductFavoriteByMemberProduct(Member member, Product product) {
		try{
			String jpql = "select favorite from ProductFavorite favorite where favorite.member = :member and favorite.product = :product ";
			return entityManager.createQuery(jpql, ProductFavorite.class).setFlushMode(FlushModeType.COMMIT).setParameter("member", member).setParameter("product", product).getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
		
	}
	
}
