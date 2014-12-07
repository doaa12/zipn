/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Constants;
import cn.bmwm.common.Result;
import cn.bmwm.modules.shop.controller.app.vo.AdvertiseCategory;
import cn.bmwm.modules.shop.controller.app.vo.Item;
import cn.bmwm.modules.shop.controller.app.vo.ItemCategory;
import cn.bmwm.modules.shop.entity.AppAdvertise;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.AppAdvertiseService;
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
	
	@Resource(name = "appAdvertiseServiceImpl")
	private AppAdvertiseService appAdvertiseService;
	
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
	public Result list(Long catId, String city) {
		
		if(city == null || city.trim().equals("")) {
			throw new BusinessException(" Parameter 'city' can not be empty ! ");
		}
		
		ProductCategory category = productCategoryService.find(catId);
		
		if(category == null) {
			throw new BusinessException(" Invalid Parameter 'catId' ! ");
		}
		
		List<ProductCategory> children = category.getChildren();
		
		//顶级分类下顶部广告
		List<AppAdvertise> appTopAdvertiseList = appAdvertiseService.findByCity(city, 3);
		AdvertiseCategory topAdvertiseCategory = getAdvertiseCategory(appTopAdvertiseList);
		
		List<ItemCategory<Item>> products = new LinkedList<ItemCategory<Item>>();
		
		List<ItemCategory<Item>> shops = new LinkedList<ItemCategory<Item>>();
		
		for(ProductCategory cat : children) {
			
			List<Product> list = productService.findRecommendList(city, cat);
			
			if(list == null || list.size() == 0){
				list = productService.findHotList(city, cat);
			}
			
			ItemCategory<Item> productItemCategory = this.getProductItemCategory(cat, list);
			
			List<Shop> shopList = shopService.findRecommendList(city, cat);
			
			if(shopList == null || shopList.size() == 0) continue;
			
			ItemCategory<Item> shopItemCategory = this.getShopItemCategory(cat, shopList);
			
			if(productItemCategory != null) products.add(productItemCategory);
			
			if(shopItemCategory != null) shops.add(shopItemCategory);
			
		}
		
		List<Object> itemCategoryList = new ArrayList<Object>();
		
		if(topAdvertiseCategory != null) {
			itemCategoryList.add(topAdvertiseCategory);
		}
		
		if(shops.size() > 0) itemCategoryList.addAll(shops);
		
		if(products.size() > 0) itemCategoryList.addAll(products);
		
		return new Result(Constants.SUCCESS, 1, "", itemCategoryList);

	}
	
}
