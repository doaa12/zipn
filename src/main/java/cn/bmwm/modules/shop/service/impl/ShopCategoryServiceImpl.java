package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.ShopCategoryDao;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopCategory;
import cn.bmwm.modules.shop.service.ShopCategoryService;

/**
 * Service - 店铺商品分类
 * @author zby
 * 2014-8-20 下午8:50:48
 */
@Service("shopCategoryServiceImpl")
public class ShopCategoryServiceImpl extends BaseServiceImpl<ShopCategory,Long> implements ShopCategoryService {

	@Resource(name = "shopCategoryDaoImpl")
	private ShopCategoryDao shopCategoryDao;
	
	@Resource(name = "shopCategoryDaoImpl")
	public void setBaseDao(ShopCategoryDao shopCategoryDao) {
		super.setBaseDao(shopCategoryDao);
	}
	
	/**
	 * 获取店铺所有分类
	 * @return
	 */
	public List<ShopCategory> findAllShopCategories(Shop shop) {
		return shopCategoryDao.findAllShopCategories(shop);
	}
	
}
