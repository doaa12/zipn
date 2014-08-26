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
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ProductService;


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
	
	
	/**
	 * App 首页请求
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> index(String city) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		List<ProductCategory> productCategories = productCategoryService.findHierarchicalTree();
		
		result.put("categories", productCategories);
		
		List<Map<String,Object>> productList = new LinkedList<Map<String,Object>>();
		
		for(ProductCategory category : productCategories) {
			
			List<Product> list = productService.findRecommendList(city, category);
			
			if(list == null || list.size() == 0){
				list = productService.findHotList(city, category);
			}
			
			Map<String,Object> products = new HashMap<String,Object>();
			products.put("category", category);
			products.put("products", list);
			productList.add(products);
			
		}
		
		result.put("products", productList);
		
		return result;
		
	}
	
}
