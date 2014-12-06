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
import cn.bmwm.modules.shop.dao.PromotionDao;
import cn.bmwm.modules.shop.entity.Promotion;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.PromotionService;

/**
 * Service - 促销
 * 
 *
 * @version 1.0
 */
@Service("promotionServiceImpl")
public class PromotionServiceImpl extends BaseServiceImpl<Promotion, Long> implements PromotionService {

	@Resource(name = "promotionDaoImpl")
	private PromotionDao promotionDao;

	@Resource(name = "promotionDaoImpl")
	public void setBaseDao(PromotionDao promotionDao) {
		super.setBaseDao(promotionDao);
	}

	public List<Promotion> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders) {
		return promotionDao.findList(hasBegun, hasEnded, count, filters, orders);
	}
	
	/**
	 * 查询店铺促销
	 * @param shop
	 * @return
	 */
	public List<Promotion> findShopPromotionList(Shop shop) {
		return promotionDao.findShopPromotionList(shop);
	}
	
	/**
	 * 查询店铺促销商品数量
	 * @param shop
	 * @return
	 */
	@Cacheable(value = "promotion", key = "#shop.id + 'findShopPromotionCount'")
	public Long findShopPromotionCount(Shop shop) {
		return promotionDao.findShopPromotionCount(shop);
	}

	@Cacheable("promotion")
	public List<Promotion> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders, String cacheRegion) {
		return promotionDao.findList(hasBegun, hasEnded, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public void save(Promotion promotion) {
		super.save(promotion);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public Promotion update(Promotion promotion) {
		return super.update(promotion);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public Promotion update(Promotion promotion, String... ignoreProperties) {
		return super.update(promotion, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}
	
	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public void delete(Shop shop, Long ... ids) {
		if(ids != null) {
			for(Long id : ids) {
				delete(promotionDao.find(shop, id));
			}
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public void delete(Promotion promotion) {
		super.delete(promotion);
	}

}