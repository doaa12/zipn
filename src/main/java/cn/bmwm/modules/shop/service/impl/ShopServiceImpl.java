/**
 * 
 */
package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.controller.app.vo.ItemPage;
import cn.bmwm.modules.shop.dao.ShopDao;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
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
	@Cacheable(value = "shop", key = "#city + #category.id + 'findRecommendList'")
	public List<Shop> findRecommendList(String city, ProductCategory category) {
		return shopDao.findRecommendList(city, category);
	}
	
	/**
	 * 查找店铺列表
	 * @param city : 城市
	 * @param category : 分类
	 * @param page : 页码
	 * @param size : 每页记录数
	 * @param x : 纬度
	 * @param y : 经度
	 * @return
	 */
	@Cacheable(value = "shop", key = "#city + #category.id + #page + #size + #order + #x + #y + 'findList'")
	public ItemPage<Shop> findList(String city, ProductCategory category, Integer page, Integer size, Integer order, Integer x, Integer y) {
		return shopDao.findList(city, category, page, size, order, x, y);
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
	
}
