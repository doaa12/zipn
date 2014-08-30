/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.modules.shop.controller.app.vo.ItemPage;
import cn.bmwm.modules.shop.controller.app.vo.ItemProduct;
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
	 * catId : 分类ID
	 * city : 城市
	 * page : 页码
	 * size : 每页显示商品数
	 * order : 排序方式，1：促销，2：新品，3：销量，4：推荐
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> list(Long catId, String city, Integer page, Integer size, Integer order) {
		
		ProductCategory category = productCategoryService.find(catId);
		
		if(page == null) page = 1;
		if(size == null) size = 10;
		if(order == null) order = 1;
		
		ItemPage<Product> itemPage = productService.findProductList(city, category, page, size, order);
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", getProductItems(itemPage.getList()));
		
		return result;
		
	}
	
	/**
	 * 获取商品列表
	 * @param productList
	 * @return
	 */
	public List<ItemProduct> getProductItems(List<Product> productList) {
		
		List<ItemProduct> list = new ArrayList<ItemProduct>();
		
		if(productList == null || productList.size() == 0){
			return list;
		}

		for(Product product : productList) {
			
			ItemProduct item = new ItemProduct();
			item.setId(product.getId());
			item.setName(product.getName());
			item.setPrice(product.getPrice().doubleValue());
			item.setImageurl(product.getImage());
			item.setEvaluateCount(product.getScoreCount());
			item.setAttribute1(product.getAttributeValue0());
			item.setAttribute2(product.getAttributeValue1());
			
			list.add(item);
			
		}
		
		return list;
		
	}
	
}
