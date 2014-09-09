/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.modules.shop.controller.app.vo.ItemCategory;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ProductService;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.sys.exception.BusinessException;

/**
 * 商品分类
 * @author zhoupuyue
 * @date 2014-8-28
 */
@Controller("appCategoryController")
@RequestMapping(value = "/app")
public class CategoryController extends AppBaseController {

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	
	/**
	 * 一级分类,按子分类返回该分类下的的推荐店铺和推荐商品
	 * @param catId : 分类ID
	 * @param city : 城市
	 * @param type : 1:商品列表,2:店铺列表,默认是商品列表
	 * @param page : 页码
	 * @param size : 每页显示记录数量
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> list(Long catId, String city) {
		
		if(city == null || city.trim().equals("")) {
			throw new BusinessException(" Parameter 'city' can not be empty ! ");
		}
		
		ProductCategory category = productCategoryService.find(catId);
		
		if(category == null) {
			throw new BusinessException(" Invalid Parameter 'catId' ! ");
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		List<ProductCategory> children = category.getChildren();
		
		List<ItemCategory> products = new LinkedList<ItemCategory>();
		
		List<ItemCategory> shops = new LinkedList<ItemCategory>();
		
		for(ProductCategory cat : children) {
			
			List<Product> list = productService.findRecommendList(city, cat);
			
			if(list == null || list.size() == 0){
				list = productService.findHotList(city, cat);
			}
			
			ItemCategory productItemCategory = this.getProductItemCategory(cat, list);
			
			List<Shop> shopList = shopService.findRecommendList(city, cat);
			
			if(shopList == null || shopList.size() == 0) continue;
			
			ItemCategory shopItemCategory = this.getShopItemCategory(cat, shopList);
			
			if(productItemCategory != null) products.add(productItemCategory);
			
			if(shopItemCategory != null) shops.add(shopItemCategory);
			
		}
		
		List<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();
		
		if(shops.size() > 0) itemCategoryList.addAll(shops);
		
		if(products.size() > 0) itemCategoryList.addAll(products);
		
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", itemCategoryList);
		
		return result;

	}
	
}
