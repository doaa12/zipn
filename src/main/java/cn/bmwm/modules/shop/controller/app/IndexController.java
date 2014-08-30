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
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ProductService;
import cn.bmwm.modules.shop.service.ShopFavoriteService;
import cn.bmwm.modules.shop.service.ShopService;


/**
 * App - 首页
 * @author zby
 * 2014-8-25 下午8:49:00
 */
@Controller("appIndexController")
@RequestMapping(value = "/app")
public class IndexController extends AppBaseController {
	
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "shopFavoriteServiceImpl")
	private ShopFavoriteService shopFavoriteService;
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	/**
	 * App - 首页
	 * 根据不同城市,查找该城市顶级分类下的置顶店铺和置顶商品,以及所有商品分类
	 * 商品查找:首先根据管理员设置查找,如果管理员没有设置置顶商品,再根据销量查找,最多返回10个商品
	 * 店铺查找:根据管理员设置查找,如果管理员没有设置置顶店铺,忽略该分类
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> index(String city) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		List<ProductCategory> categories = productCategoryService.findRoots();
		
		List<ItemCategory> products = new LinkedList<ItemCategory>();
		
		List<ItemCategory> shops = new LinkedList<ItemCategory>();
		
		for(ProductCategory category : categories) {
			
			List<Product> productList = productService.findRecommendList(city, category);
			
			if(productList == null || productList.size() == 0){
				productList = productService.findHotList(city, category);
			}
			
			ItemCategory productItemCategory = this.getProductItemCategory(category, productList);
			
			List<Shop> shopList = shopService.findRecommendList(city, category);
			
			ItemCategory shopItemCategory = this.getShopItemCategory(category, shopList);
			
			if(productItemCategory != null) products.add(productItemCategory);
			
			if(shopItemCategory != null) shops.add(shopItemCategory);
			
		}
		
		List<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();
		
		if(shops.size() > 0) itemCategoryList.addAll(shops);
		
		if(products.size() > 0) itemCategoryList.addAll(products);
		
		//TODO:收藏店铺动态，待定，需要登录后才可以显示
		//List<Shop> shopList = shopFavoriteService.findDynamicShops(memberService.getCurrent());
		
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", itemCategoryList);
		result.put("categories", categories);
		
		return result;
		
	}
	
}
