/**
 * 
 */
package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
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
	 * 查找店铺分页
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
