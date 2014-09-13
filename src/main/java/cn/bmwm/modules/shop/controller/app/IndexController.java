package cn.bmwm.modules.shop.controller.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.modules.shop.controller.app.vo.AdvertiseCategory;
import cn.bmwm.modules.shop.controller.app.vo.Item;
import cn.bmwm.modules.shop.controller.app.vo.ItemCategory;
import cn.bmwm.modules.shop.controller.app.vo.ShopDynamic;
import cn.bmwm.modules.shop.entity.AppAdvertise;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.VirtualShopCategory;
import cn.bmwm.modules.shop.service.AppAdvertiseService;
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
	
	@Resource(name = "appAdvertiseServiceImpl")
	private AppAdvertiseService appAdvertiseService;
	
	/**
	 * App - 首页
	 * 根据不同城市,查找该城市顶级分类下的置顶店铺和置顶商品,以及所有商品分类
	 * 查询虚拟分类下的推荐店铺和推荐商品
	 * 商品查找:首先根据管理员设置查找,如果管理员没有设置置顶商品,再根据销量查找,最多返回10个商品
	 * 店铺查找:根据管理员设置查找,如果管理员没有设置置顶店铺,忽略该分类
	 * 收藏店铺动态，以及收藏店铺发布新品
	 * 首页查询用户收藏店铺动态；如果用户收藏店铺动态为空，查询收藏次数最多的店铺的动态
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
		
		//TODO：收藏店铺动态,根据用户最后一次登录时间来获取收藏店铺动态；如果收藏动态为空,取收藏最多的店铺的动态
		//List<Shop> shopList = shopFavoriteService.findDynamicShops(memberService.getCurrent());
		
		//首页按用户最近登录时间获取新品；如果用户收藏为空,按15天来获取收藏最多的店铺的动态
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -15);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date time = calendar.getTime();
		
		//TODO：先按用户收藏店铺来获取店铺动态；如果收藏动态为空,获取收藏次数前5的店铺动态
		List<Shop> favoriteShopList = shopService.findFavoriteTopShopList(city);
		
		//获取收藏店铺发布新品
		List<Product> favoriteProductList = productService.findShopNewList(favoriteShopList, time);
		
		ItemCategory<ShopDynamic> favoriteShopCategory = this.getFavoriteShopItemCategory(favoriteShopList, favoriteProductList);
		
		List<ItemCategory<Item>> products = new LinkedList<ItemCategory<Item>>();
		List<ItemCategory<Item>> shops = new LinkedList<ItemCategory<Item>>();
		List<ItemCategory<Item>> virtualShops = new LinkedList<ItemCategory<Item>>();
		
		//虚拟店铺分类
		List<VirtualShopCategory> virtualShopCategoryList = virtualShopCategoryService.findList(city);
		
		if(virtualShopCategoryList != null && virtualShopCategoryList.size() > 0) {
			for(VirtualShopCategory virtualShopCategory : virtualShopCategoryList) {
				List<Shop> shopList = shopService.findVirtualCategoryShopList(virtualShopCategory.getId());
				ItemCategory<Item> shopVirtualCategory = getVirtualShopCategory(virtualShopCategory, shopList);
				if(shopVirtualCategory != null) {
					virtualShops.add(shopVirtualCategory);
				}
			}
		}
		
		//广告
		AppAdvertise appAdvertise = appAdvertiseService.findByCity(city);
		AdvertiseCategory advertiseCategory = getAdvertiseCategory(appAdvertise);
		
		List<ProductCategory> categories = productCategoryService.findRoots();
		
		
		//查找每个顶级分类下的推荐店铺和推荐商品
		for(ProductCategory category : categories) {
			
			List<Product> productList = productService.findRecommendList(city, category);
			
			/*
			if(productList == null || productList.size() == 0){
				productList = productService.findHotList(city, category);
			}
			*/
			
			ItemCategory<Item> productItemCategory = this.getProductItemCategory(category, productList);
			
			List<Shop> shopList = shopService.findRecommendList(city, category);
			
			ItemCategory<Item> shopItemCategory = this.getShopItemCategory(category, shopList);
			
			if(productItemCategory != null) products.add(productItemCategory);
			
			if(shopItemCategory != null) shops.add(shopItemCategory);
			
		}
		
		List<Object> itemCategoryList = new ArrayList<Object>();
		
		if(favoriteShopCategory != null) {
			itemCategoryList.add(favoriteShopCategory);
		}
		
		if(virtualShops.size() > 0) itemCategoryList.addAll(virtualShops);
		
		if(advertiseCategory != null) {
			itemCategoryList.add(advertiseCategory);
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
	 * @param productList
	 * @return
	 */
	public ItemCategory<ShopDynamic> getFavoriteShopItemCategory(List<Shop> shopList, List<Product> productList) {
		
		if(shopList == null || shopList.size() == 0) {
			return null;
		}
		
		if(productList == null || productList.size() == 0) {
			return null;
		}
		
		List<ShopDynamic> itemList = new ArrayList<ShopDynamic>();
		
		for(Shop shop : shopList) {
			
			List<Item> products = new ArrayList<Item>();
			
			for(Product product : productList) {
				if(product.getShop().equals(shop)) {
					Item item = new Item();
					item.setCode(product.getId());
					item.setImageurl(product.getImage());
					item.setTitle(product.getName());
					item.setType(2);
					products.add(item);
				}
			}
			
			if(products == null || products.size() == 0) {
				continue;
			}
			
			ShopDynamic item = new ShopDynamic();
			item.setCode(shop.getId());
			item.setTitle(shop.getName());
			item.setType(1);
			item.setImageurl(shop.getImage());
			item.setUpdateTime(getTime(shop.getModifyDate()));
			item.setList(products);
			
			itemList.add(item);
			
		}
		
		ItemCategory<ShopDynamic> itemCategory = new ItemCategory<ShopDynamic>();
		itemCategory.setCode(0L);
		itemCategory.setShowmore(0);
		itemCategory.setShowtype(1);
		itemCategory.setTitle("动态");
		itemCategory.setDataList(itemList);
		itemCategory.setMoretype(1);
		
		return itemCategory;
		
	}
	
	/**
	 * 获取首页虚拟店铺分类
	 * @param category
	 * @param list
	 * @return
	 */
	protected ItemCategory<Item> getVirtualShopCategory(VirtualShopCategory category, List<Shop> list) {
		
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
		
		ItemCategory<Item> itemCategory = new ItemCategory<Item>();
		itemCategory.setCode(category.getId());
		itemCategory.setShowmore(0);
		itemCategory.setShowtype(3);
		itemCategory.setTitle(category.getName());
		itemCategory.setDataList(itemList);
		itemCategory.setMoretype(1);
		
		return itemCategory;
		
	}
	
	/**
	 * 获取首页广告
	 * @param appAdvertise
	 * @return
	 */
	public AdvertiseCategory getAdvertiseCategory(AppAdvertise appAdvertise) {
		
		if(appAdvertise == null) return null;
		
		AdvertiseCategory category = new AdvertiseCategory();
		category.setShowmore(0);
		category.setShowtype(4);
		category.setImageurl(appAdvertise.getImageUrl());
		category.setLinkurl(appAdvertise.getLinkUrl());
		
		return category;
		
	}
	
	/**
	 * 获取发布时间
	 * @param time
	 * @return
	 */
	public String getTime(Date time) {
		
		Date now = new Date();
		long lnow = now.getTime();
		long ltime = time.getTime();
		
		long sub = lnow - ltime;
		
		if(sub < 60 * 60 * 1000L) {
			return (sub / (60 * 1000L)) + "分钟前";
		}else if(sub < 24 * 60 * 60 * 1000L) {
			return (sub / (60 * 60 * 1000L)) + "小时前";
		}else {
			return (sub / (24 * 60 * 60 * 1000L)) + "天前";
		}
		
	}
	
}
