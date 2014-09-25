/**
 * 
 */
package cn.bmwm.modules.shop.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.controller.app.vo.ItemPage;
import cn.bmwm.modules.shop.dao.ShopDao;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.VirtualShopCategory;
import cn.bmwm.modules.shop.service.ShopService;

/**
 * Service - 店铺
 * @author zhoupuyue
 * @date 2014-8-22
 */
@Service("shopServiceImpl")
public class ShopServiceImpl extends BaseServiceImpl<Shop,Long> implements ShopService {
	
	@Resource(name = "shopDaoImpl")
	private ShopDao shopDao;
	
	@Resource(name = "shopDaoImpl")
	public void setBaseDao(ShopDao shopDao){
		super.setBaseDao(shopDao);
	}
	
	/**
	 * 查找推荐店铺
	 * @param category
	 * @param city
	 * @return
	 */
	@Cacheable(value = "shop", key = "'city' + #city + 'category' + #category + 'findRecommendList'")
	public List<Shop> findRecommendList(String city, ProductCategory category) {
		return shopDao.findRecommendList(city, category);
	}
	
	/**
	 * 查找收藏数量前几名的店铺
	 * @param city
	 * @return
	 */
	@Cacheable(value = "shop", key = "'city' + #city + 'findFavoriteTopShopList'")
	public List<Shop> findFavoriteTopShopList(String city) {
		return shopDao.findFavoriteTopShopList(city);
	}
	
	/**
	 * 查找附近店铺列表
	 * @param city : 城市
	 * @param catId : 类目ID
	 * @param page : 页码
	 * @param size : 每页记录数
	 * @param x : 经度
	 * @param y : 纬度
	 * @return
	 */
	@Cacheable(value = "shop", key = "'city' + #city + 'catId' + #catId + 'page' + #page + 'size' + #size + 'order' + #order + 'x' + #x + 'y' + #y + 'findList'")
	public ItemPage<Shop> findList(String city, Integer catId, Integer page, Integer size, Integer order, BigDecimal x, BigDecimal y) {
		return shopDao.findList(city, catId, page, size, order, x, y);
	}
	
	/**
	 * 查询虚拟分类店铺
	 * @param category
	 * @return
	 */
	@Cacheable(value = "shop", key = "'catId' + #catId + 'findVirtualCategoryShopList'")
	public List<Shop> findVirtualCategoryShopList(Long catId) {
		return shopDao.findVirtualCategoryShopList(catId);
	}
	
	/**
	 * 后台管理查找店铺分页
	 * @param productCategory
	 * @param city
	 * @param pageable
	 * @return
	 */
	public Page<Shop> findPage(ProductCategory productCategory, String city, Boolean isTop, Boolean isList, Pageable pageable) {
		return shopDao.findPage(productCategory, city, isTop, isList, pageable);
	}
	
	/**
	 * 获取所有开通的城市
	 * @return
	 */
	public List<String> findAllCities() {
		return shopDao.findAllCities();
	}
	
	@Override
	@Transactional
	@CacheEvict(value = {"shop", "productCategory"}, allEntries = true)
	public void save(Shop shop) {
		Assert.notNull(shop);
		super.save(shop);
		shopDao.flush();
	}

	@Override
	@Transactional
	@CacheEvict(value = { "shop", "productCategory"}, allEntries = true)
	public Shop update(Shop shop) {
		
		Assert.notNull(shop);
		
		Shop pshop = super.update(shop);
		
		List<VirtualShopCategory> virtualCategories = shop.getVirtualCategories();
		
		if(virtualCategories != null && virtualCategories.size() > 0) {
			for(VirtualShopCategory category : virtualCategories) {
				category.getShops().add(shop);
			}
		}
		
		shopDao.flush();
		
		return pshop;
		
	}
	
}
