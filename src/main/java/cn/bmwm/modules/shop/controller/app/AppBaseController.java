package cn.bmwm.modules.shop.controller.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.bmwm.modules.shop.controller.admin.BaseController;
import cn.bmwm.modules.shop.controller.app.vo.AdvertiseCategory;
import cn.bmwm.modules.shop.controller.app.vo.AdvertiseItem;
import cn.bmwm.modules.shop.controller.app.vo.Item;
import cn.bmwm.modules.shop.controller.app.vo.ItemCategory;
import cn.bmwm.modules.shop.controller.app.vo.ItemProduct;
import cn.bmwm.modules.shop.controller.app.vo.ItemShop;
import cn.bmwm.modules.shop.entity.AppAdvertise;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.LBSService;

/**
 * App - 基础Controller
 * @author zby
 * 2014-8-30 上午11:01:27
 */
public class AppBaseController extends BaseController {
	
	@Resource(name = "lbsServiceImpl")
	private LBSService lbsService;

	
	/**
	 * 获取首页置顶商品
	 * @param category
	 * @param list
	 * @return
	 */
	protected ItemCategory<Item> getProductItemCategory(ProductCategory category, List<Product> list) {
		
		if(list == null || list.size() == 0) return null;
		
		List<Item> itemList = new ArrayList<Item>();
		
		for(Product product : list) {
			Item item = new Item();
			item.setCode(product.getId());
			item.setTitle(product.getName());
			item.setType(2);
			item.setImageurl(product.getImage());
			item.setPrice(product.getPrice().doubleValue());
			itemList.add(item);
		}
		
		ItemCategory<Item> itemCategory = new ItemCategory<Item>();
		itemCategory.setCode(category.getId());
		itemCategory.setShowmore(1);
		itemCategory.setShowtype(3);
		itemCategory.setTitle(category.getName());
		itemCategory.setDataList(itemList);
		itemCategory.setMoretype(2);
		
		return itemCategory;
		
	}
	
	/**
	 * 获取首页置顶店铺
	 * @param category
	 * @param list
	 * @return
	 */
	protected ItemCategory<Item> getShopItemCategory(ProductCategory category, List<Shop> list) {
		
		if(list == null || list.size() == 0) return null;
		
		List<Item> itemList = new ArrayList<Item>();
		
		for(Shop shop : list) {
			Item item = new Item();
			item.setCode(shop.getId());
			item.setTitle(shop.getName());
			item.setType(1);
			item.setImageurl(shop.getImage());
			item.setArea(shop.getRegion());
			item.setPrice(shop.getAvgPrice());
			itemList.add(item);
		}
		
		ItemCategory<Item> itemCategory = new ItemCategory<Item>();
		itemCategory.setCode(category.getId());
		itemCategory.setShowmore(1);
		itemCategory.setShowtype(3);
		itemCategory.setTitle(category.getName());
		itemCategory.setDataList(itemList);
		itemCategory.setMoretype(1);
		
		return itemCategory;
		
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
			item.setOriginalPrice(product.getMarketPrice().doubleValue());
			item.setImageurl(product.getImage());
			item.setEvaluateCount(product.getScoreCount());
			item.setAttribute1(product.getAttributeValue0());
			item.setAttribute2(product.getAttributeValue1());
			
			list.add(item);
			
		}
		
		return list;
		
	}
	
	/**
	 * 获取店铺列表
	 * @param shopList
	 * @return
	 */
	public List<ItemShop> getShopItems(List<Shop> shopList, Double x, Double y) {
		
		List<ItemShop> list = new ArrayList<ItemShop>();
		
		if(shopList == null || shopList.size() == 0 ) return list;
		
		for(Shop shop : shopList) {
			
			ItemShop item = new ItemShop();
			item.setCode(shop.getId());
			item.setTitle(shop.getName());
			item.setDistance(getDistance(shop, x, y));
			item.setPrice(shop.getAvgPrice());
			item.setScore(shop.getAvgScore());
			item.setStatus(shop.getStatus());
			item.setCategory(shop.getCategoryName());
			item.setArea(shop.getRegion());
			item.setImageurl(shop.getImage());
			
			if(shop.getShopType() != null) {
				item.setType(shop.getShopType());
			}
			
			list.add(item);
			
		}
		
		return list;
		
	}
	
	/**
	 * 计算距离,x:经度,y:纬度
	 * @param x
	 * @param y
	 * @return
	 */
	public String getDistance(Shop shop, Double x, Double y) {
		
		if(x == null || y == null) {
			return "";
		}
		
		if(shop.getLatitude() == null || shop.getLongitude() == null) {
			return "";
		}
		
		double distance = lbsService.getDistance(y, x, shop.getLatitude().doubleValue(), shop.getLongitude().doubleValue());
		
		if(distance < 1) {
			return (int)(distance * 1000) + "米";
		}else{
			return (long)distance + "千米";
		}
		
	}
	
	/**
	 * 获取首页广告
	 * @param appAdvertise
	 * @return
	 */
	public AdvertiseCategory getAdvertiseCategory(List<AppAdvertise> list) {
		
		if(list == null || list.size() == 0) return null;
		
		AdvertiseCategory category = new AdvertiseCategory();
		category.setShowmore(0);
		category.setShowtype(4);
		
		List<AdvertiseItem> itemList = new ArrayList<AdvertiseItem>();
		
		for(AppAdvertise ad : list) {
			AdvertiseItem item = new AdvertiseItem();
			item.setImageurl(ad.getImageUrl());
			item.setLinkurl(ad.getLinkUrl());
			itemList.add(item);
		}
		
		category.setDataList(itemList);
		
		return category;
		
	}
	
}
