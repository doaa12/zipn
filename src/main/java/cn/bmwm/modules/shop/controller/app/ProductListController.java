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
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ProductService;

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
	
	/**
	 * 商品列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> list(Long catId, String city, Integer page, Integer size) {
		
		ProductCategory category = productCategoryService.find(catId);
		
		List<ProductCategory> categories = productCategoryService.findHierarchicalTree();
		
		//顶级分类,显示下级分类商品
		if(category.getGrade() == 0) {
			
			Map<String,Object> result = new HashMap<String,Object>();
			
			List<ProductCategory> children = category.getChildren();
			
			List<Map<String,Object>> products = new LinkedList<Map<String,Object>>();
			
			for(ProductCategory cat : children) {
				
				List<Product> list = productService.findRecommendList(city, cat);
				
				if(list == null || list.size() == 0){
					list = productService.findHotList(city, cat);
				}
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("categoryId", cat.getId());
				map.put("categoryName", cat.getName());
				map.put("productList", list);
				
				products.add(map);
				
			}
			
			result.put("products", products);
			result.put("catetories", categories);
			
			return result;
		
		//显示商品列表
		}else{
			
			if(page == null) page = 1;
			if(size == null) size = 10;
			
			Map<String,Object> result = productService.findList(city, category, page, size);
			result.put("categories", categories);
			
			return result;
			
		}
		
	}
	
}
