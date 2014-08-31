package cn.bmwm.modules.shop.service;

import java.util.List;

import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopCategory;

/**
 * Service - 店铺商品分类
 * @author zby
 * 2014-8-20 下午8:49:06
 */
public interface ShopCategoryService extends BaseService<ShopCategory,Long> {

	/**
	 * 获取店铺所有分类
	 * @return
	 */
	List<ShopCategory> findAllShopCategories(Shop shop);
	
}
