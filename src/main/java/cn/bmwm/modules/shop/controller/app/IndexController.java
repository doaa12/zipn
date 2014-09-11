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

import cn.bmwm.modules.shop.controller.app.vo.Item;
import cn.bmwm.modules.shop.controller.app.vo.ItemCategory;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.VirtualShopCategory;
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ProductService;
import cn.bmwm.modules.shop.service.ShopFavoriteService;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.shop.service.VirtualShopCategoryService;
import cn.bmwm.modules.sys.exception.BusinessException;


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
	
	@Resource(name = "virtualShopCategoryServiceImpl")
	private VirtualShopCategoryService virtualShopCategoryService;
	
	/**
	 * App - 首页
	 * 根据不同城市,查找该城市顶级分类下的置顶店铺和置顶商品,以及所有商品分类
	 * 商品查找:首先根据管理员设置查找,如果管理员没有设置置顶商品,再根据销量查找,最多返回10个商品
	 * 店铺查找:根据管理员设置查找,如果管理员没有设置置顶店铺,忽略该分类
	 * 收藏店铺动态，以及收藏店铺发布新品
	 * 广告位
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> index(String city) {
		
		if(city == null || city.trim().equals("")) {
			throw new BusinessException(" Parameter 'city' can not be empty ! ");
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		//TODO:收藏店铺动态，待定，需要登录后才可以显示
		//List<Shop> shopList = shopFavoriteService.findDynamicShops(memberService.getCurrent());
		
		List<Shop> favoriteShopList = shopService.findFavoriteTopShopList(city);
		ItemCategory favoriteShopCategory = this.getFavoriteShopItemCategory(favoriteShopList);
		
		//发布新品
		List<Product> favoriteProductList = productService.findNewList(city);
		ItemCategory favoriteProductCategory = this.getFavoriteProductItemCategory(favoriteProductList);
		
		List<ItemCategory> products = new LinkedList<ItemCategory>();
		
		List<ItemCategory> shops = new LinkedList<ItemCategory>();
		
		//虚拟店铺分类
		List<VirtualShopCategory> virtualShopCategoryList = virtualShopCategoryService.findList(city);
		
		if(virtualShopCategoryList != null && virtualShopCategoryList.size() > 0) {
			for(VirtualShopCategory virtualShopCategory : virtualShopCategoryList) {
				List<Shop> shopList = shopService.findVirtualCategoryShopList(virtualShopCategory.getId());
				ItemCategory shopVirtualCategory = getVirtualShopCategory(virtualShopCategory, shopList);
				shops.add(shopVirtualCategory);
			}
		}
		
		List<ProductCategory> categories = productCategoryService.findRoots();
		
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
		
		if(favoriteShopCategory != null) {
			itemCategoryList.add(favoriteShopCategory);
		}
		
		if(favoriteProductCategory != null) {
			itemCategoryList.add(favoriteProductCategory);
		}
		
		if(shops.size() > 0) itemCategoryList.addAll(shops);
		
		if(products.size() > 0) itemCategoryList.addAll(products);
		
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", itemCategoryList);
		result.put("categories", categories);
		
		return result;
		
	}
	
	/**
	 * 获取首页收藏店铺动态
	 * @param shopList
	 * @return
	 */
	public ItemCategory getFavoriteShopItemCategory(List<Shop> shopList) {
		
		if(shopList == null || shopList.size() == 0) {
			return null;
		}
		
		List<Item> itemList = new ArrayList<Item>();
		
		for(Shop shop : shopList) {
			Item item = new Item();
			item.setCode(shop.getId());
			item.setTitle(shop.getName());
			item.setType(1);
			item.setImageurl(shop.getImage());
			item.setArea(shop.getRegion());
			itemList.add(item);
		}
		
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setCode(0L);
		itemCategory.setShowmore(0);
		itemCategory.setShowtype(1);
		itemCategory.setTitle("动态");
		itemCategory.setDataList(itemList);
		itemCategory.setMoretype(1);
		
		return itemCategory;
		
	}
	
	/**
	 * 获取首页收藏店铺发布新品
	 * @param shopList
	 * @return
	 */
	public ItemCategory getFavoriteProductItemCategory(List<Product> list) {
		
		if(list == null || list.size() == 0) {
			return null;
		}
		
		List<Item> itemList = new ArrayList<Item>();
		
		for(Product product : list) {
			Item item = new Item();
			item.setCode(product.getId());
			item.setTitle(product.getName());
			item.setType(2);
			item.setImageurl(product.getImage());
			item.setArea(product.getRegion());
			itemList.add(item);
		}
		
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setCode(0L);
		itemCategory.setShowmore(0);
		itemCategory.setShowtype(2);
		itemCategory.setTitle("");
		itemCategory.setDataList(itemList);
		itemCategory.setMoretype(2);
		
		return itemCategory;
		
	}
	
	/**
	 * 获取首页虚拟店铺分类
	 * @param category
	 * @param list
	 * @return
	 */
	protected ItemCategory getVirtualShopCategory(VirtualShopCategory category, List<Shop> list) {
		
		if(list == null || list.size() == 0) return null;
		
		List<Item> itemList = new ArrayList<Item>();
		
		for(Shop shop : list) {
			Item item = new Item();
			item.setCode(shop.getId());
			item.setTitle(shop.getName());
			item.setType(1);
			item.setImageurl(shop.getImage());
			item.setArea(shop.getRegion());
			itemList.add(item);
		}
		
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setCode(category.getId());
		itemCategory.setShowmore(0);
		itemCategory.setShowtype(3);
		itemCategory.setTitle(category.getName());
		itemCategory.setDataList(itemList);
		itemCategory.setMoretype(1);
		
		return itemCategory;
		
	}
	
}
