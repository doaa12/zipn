/**
 * 
 */
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

import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ProductService;
import cn.bmwm.modules.shop.service.ShopService;

/**
 * App - 商品列表
 * @author zhoupuyue
 * @date 2014-8-27
 */
@Controller("appProductListController")
@RequestMapping(value = "/app")
public class ProductListController {

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	
	/**
	 * 商品列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> list(Long catId, String city, Integer page, Integer size) {
		
		ProductCategory category = productCategoryService.find(catId);
		
		//顶级分类,显示下级分类商品
		if(category.getGrade() == 0) {
			
			Map<String,Object> result = new HashMap<String,Object>();
			
			List<ProductCategory> children = category.getChildren();
			
			List<Map<String,Object>> products = new LinkedList<Map<String,Object>>();
			
			List<Map<String,Object>> shops = new LinkedList<Map<String,Object>>();
			
			for(ProductCategory cat : children) {
				
				List<Product> list = productService.findRecommendList(city, cat);
				
				if(list == null || list.size() == 0){
					list = productService.findHotList(city, cat);
				}
				
				Map<String,Object> productMap = new HashMap<String,Object>();
				
				productMap.put("categoryId", cat.getId());
				productMap.put("categoryName", cat.getName());
				productMap.put("productList", list);
				
				products.add(productMap);
				
				List<Shop> shopList = shopService.findRecommendList(city, category);
				
				if(shopList == null || shopList.size() == 0) continue;
				
				Map<String,Object> shopMap = new HashMap<String,Object>();
				shopMap.put("categoryId", category.getId());
				shopMap.put("categoryName", category.getName());
				shopMap.put("shopList", shopList);
				
				shops.add(shopMap);
				
			}
			
			result.put("type", 1);
			result.put("products", products);
			result.put("shops", shops);
			
			return result;
		
		//显示商品列表
		}else{
			
			if(page == null) page = 1;
			if(size == null) size = 10;
			
			Map<String,Object> result = productService.findList(city, category, page, size);
			result.put("type", 2);
			
			return result;
			
		}
		
	}
	
}
