package cn.bmwm.modules.shop.dao;

import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopReview;

/**
 * 店铺评论
 * @author zby
 * 2014-8-31 下午1:54:26
 */
public interface ShopReviewDao extends BaseDao<ShopReview,Long> {

	/**
	 * 查询店铺最新评论
	 * @param shop
	 * @return
	 */
	ShopReview findLatestReview(Shop shop);
	
}
