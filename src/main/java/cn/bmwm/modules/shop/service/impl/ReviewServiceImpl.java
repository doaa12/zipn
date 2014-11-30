/*


 * */
package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bmwm.common.persistence.Filter;
import cn.bmwm.common.persistence.Order;
import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.dao.ProductDao;
import cn.bmwm.modules.shop.dao.ReviewDao;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductReview;
import cn.bmwm.modules.shop.entity.ProductReview.Type;
import cn.bmwm.modules.shop.service.ReviewService;
import cn.bmwm.modules.shop.service.StaticService;

/**
 * Service - 评论
 * 
 *
 * @version 1.0
 */
@Service("reviewServiceImpl")
public class ReviewServiceImpl extends BaseServiceImpl<ProductReview, Long> implements ReviewService {

	@Resource(name = "reviewDaoImpl")
	private ReviewDao reviewDao;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	@Resource(name = "reviewDaoImpl")
	public void setBaseDao(ReviewDao reviewDao) {
		super.setBaseDao(reviewDao);
	}

	@Cacheable("review")
	public List<ProductReview> findList(Member member, Product product, Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		return reviewDao.findList(member, product, type, isShow, count, filters, orders);
	}

	public List<ProductReview> findList(Member member, Product product, Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders, String cacheRegion) {
		return reviewDao.findList(member, product, type, isShow, count, filters, orders);
	}

	public Page<ProductReview> findPage(Member member, Product product, Type type, Boolean isShow, Pageable pageable) {
		return reviewDao.findPage(member, product, type, isShow, pageable);
	}
	
	@Cacheable(value = "review", key = "'member' + #member + 'product' + #product + 'type' + #type + 'isShow' + #isShow + 'count'")
	public Long count(Member member, Product product, Type type, Boolean isShow) {
		return reviewDao.count(member, product, type, isShow);
	}

	public boolean isReviewed(Member member, Product product) {
		return reviewDao.isReviewed(member, product);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void save(ProductReview review) {
		super.save(review);
		Product product = review.getProduct();
		if (product != null) {
			reviewDao.flush();
			long totalScore = reviewDao.calculateTotalScore(product);
			long scoreCount = reviewDao.calculateScoreCount(product);
			product.setTotalScore(totalScore);
			product.setScoreCount(scoreCount);
			productDao.merge(product);
			reviewDao.flush();
			staticService.build(product);
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public ProductReview update(ProductReview review) {
		ProductReview pReview = super.update(review);
		Product product = pReview.getProduct();
		if (product != null) {
			reviewDao.flush();
			long totalScore = reviewDao.calculateTotalScore(product);
			long scoreCount = reviewDao.calculateScoreCount(product);
			product.setTotalScore(totalScore);
			product.setScoreCount(scoreCount);
			productDao.merge(product);
			reviewDao.flush();
			staticService.build(product);
		}
		return pReview;
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public ProductReview update(ProductReview review, String... ignoreProperties) {
		return super.update(review, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(ProductReview review) {
		if (review != null) {
			super.delete(review);
			Product product = review.getProduct();
			if (product != null) {
				reviewDao.flush();
				long totalScore = reviewDao.calculateTotalScore(product);
				long scoreCount = reviewDao.calculateScoreCount(product);
				product.setTotalScore(totalScore);
				product.setScoreCount(scoreCount);
				productDao.merge(product);
				reviewDao.flush();
				staticService.build(product);
			}
		}
	}

}