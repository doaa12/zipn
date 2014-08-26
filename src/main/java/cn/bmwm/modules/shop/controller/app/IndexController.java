package cn.bmwm.modules.shop.controller.app;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.modules.shop.controller.shop.BaseController;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ProductService;
import cn.bmwm.modules.shop.service.ShopFavoriteService;


/**
 * App - 首页
 * @author zby
 * 2014-8-25 下午8:49:00
 */
@Controller("appIndexController")
@RequestMapping(value = "/app")
public class IndexController extends BaseController {
	
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "shopFavoriteServiceImpl")
	private ShopFavoriteService shopFavoriteService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	/**
	 * App 首页
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> index(String city) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		List<ProductCategory> categories = productCategoryService.findHierarchicalTree();
		
		List<Map<String,Object>> products = new LinkedList<Map<String,Object>>();
		
		for(ProductCategory category : categories) {
			
			List<Product> list = productService.findRecommendList(city, category);
			
			if(list == null || list.size() == 0){
				list = productService.findHotList(city, category);
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("categoryId", category.getId());
			map.put("categoryName", category.getName());
			map.put("products", list);
			
			products.add(map);
			
		}
		
		List<Shop> shopList = shopFavoriteService.findDynamicShops(memberService.getCurrent());
		
		result.put("categories", categories);
		result.put("shops", shopList);
		result.put("products", products);
		
		return result;
		
	}
	
}
