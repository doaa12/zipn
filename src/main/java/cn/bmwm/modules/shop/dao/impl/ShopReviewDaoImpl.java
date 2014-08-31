package cn.bmwm.modules.shop.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.ShopReviewDao;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopReview;

/** 
 * 店铺评论
 * @author zby
 * 2014-8-31 下午1:54:48
 */
@Repository("shopReviewDaoImpl")
public class ShopReviewDaoImpl extends BaseDaoImpl<ShopReview,Long> implements ShopReviewDao {
	
	/**
	 * 查询店铺最新评论
	 * @param shop
	 * @return
	 */
	public ShopReview findLatestReview(Shop shop) {
		String jpql = " select review from ShopReview review where review.shop = :shop and review.isShow = true order by createDate desc ";
		TypedQuery<ShopReview> query = entityManager.createQuery(jpql, ShopReview.class);
		List<ShopReview> list = query.setFlushMode(FlushModeType.COMMIT).setParameter("shop", shop).setMaxResults(1).getResultList();
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
	
}
