/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.modules.shop.controller.app.vo.ItemPage;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ProductService;

/**
 * App - 商品
 * @author zhoupuyue
 * @date 2014-8-27
 */
@Controller("appProductController")
@RequestMapping(value = "/app/product")
public class ProductController {
	
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/**
	 * 商品列表
	 * 首页和一级分类下的商品推荐,点击更多,显示该分类下的商品列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ItemPage<Product> list(Long catId, String city, Integer page, Integer size) {
		
		ProductCategory category = productCategoryService.find(catId);
		
		if(page == null) page = 1;
		if(size == null) size = 10;
		
		ItemPage<Product> result = productService.findList(city, category, page, size);
		
		return result;
		
	}
	
}
