/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ShopService;

/**
 * App - 店铺
 * @author zhoupuyue
 * @date 2014-8-28
 */
@Controller("appShopController")
@RequestMapping(value = "/app/shop")
public class ShopController {
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	/**
	 * 店铺列表
	 * 首页和一级分类下的商铺推荐,点击更多,显示该分类下的店铺列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map<String,Object> list(Long catId, String city, Integer page, Integer size) {
		
		ProductCategory category = productCategoryService.find(catId);
		
		if(page == null) page = 1;
		if(size == null) size = 10;
		
		Map<String,Object> result = shopService.findList(city, category, page, size);
		
		return result;
		
	}
	
}
