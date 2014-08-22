/**
 * 
 */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.ShopDao;
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
	
	
}
