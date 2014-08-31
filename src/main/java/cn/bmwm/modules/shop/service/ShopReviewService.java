package cn.bmwm.modules.shop.service;

import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopReview;

/**
 * 店铺评论
 * @author zby
 * 2014-8-31 下午1:51:55
 */
public interface ShopReviewService {
	
	/**
	 * 查询店铺最新评论
	 * @param shop
	 * @return
	 */
	ShopReview findLatestReview(Shop shop);

}
