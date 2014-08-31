package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.ShopReviewDao;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopReview;
import cn.bmwm.modules.shop.service.ShopReviewService;

/**
 * 店铺评论
 * @author zby
 * 2014-8-31 下午1:52:44
 */
@Service("shopReviewServiceImpl")
public class ShopReviewServiceImpl extends BaseServiceImpl<ShopReview,Long> implements ShopReviewService {
	
	@Resource(name = "shopReviewDaoImpl")
	private ShopReviewDao shopReviewDao;
	
	@Resource(name = "shopReviewDaoImpl")
	public void setBaseDao(ShopReviewDao shopReviewDao) {
		super.setBaseDao(shopReviewDao);
	}

	/**
	 * 查询店铺最新评论
	 * @param shop
	 * @return
	 */
	public ShopReview findLatestReview(Shop shop) {
		return shopReviewDao.findLatestReview(shop);
	}
	
}
