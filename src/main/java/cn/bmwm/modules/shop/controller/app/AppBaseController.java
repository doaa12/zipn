package cn.bmwm.modules.shop.controller.app;

import java.util.ArrayList;
import java.util.List;

import cn.bmwm.modules.shop.controller.admin.BaseController;
import cn.bmwm.modules.shop.controller.app.vo.Item;
import cn.bmwm.modules.shop.controller.app.vo.ItemCategory;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;

/**
 * App - 基础Controller
 * @author zby
 * 2014-8-30 上午11:01:27
 */
public class AppBaseController extends BaseController {

	
	/**
	 * 获取首页置顶商品
	 * @param category
	 * @param list
	 * @return
	 */
	protected ItemCategory getProductItemCategory(ProductCategory category, List<Product> list) {
		
		if(list == null || list.size() == 0) return null;
		
		List<Item> itemList = new ArrayList<Item>();
		
		for(Product product : list) {
			Item item = new Item();
			item.setCode(product.getId());
			item.setTitle(product.getName());
			item.setType(2);
			item.setImageurl(product.getImage());
			itemList.add(item);
		}
		
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setCode(category.getId());
		itemCategory.setShowmore(1);
		itemCategory.setShowtype(3);
		itemCategory.setTitle(category.getName());
		itemCategory.setDataList(itemList);
		
		return itemCategory;
		
	}
	
	/**
	 * 获取首页置顶店铺
	 * @param category
	 * @param list
	 * @return
	 */
	protected ItemCategory getShopItemCategory(ProductCategory category, List<Shop> list) {
		
		if(list == null || list.size() == 0) return null;
		
		List<Item> itemList = new ArrayList<Item>();
		
		for(Shop shop : list) {
			Item item = new Item();
			item.setCode(shop.getId());
			item.setTitle(shop.getName());
			item.setType(1);
			item.setImageurl(shop.getImage());
			itemList.add(item);
		}
		
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setCode(category.getId());
		itemCategory.setShowmore(1);
		itemCategory.setShowtype(3);
		itemCategory.setTitle(category.getName());
		itemCategory.setDataList(itemList);
		
		return itemCategory;
		
	}
	
	/**
	 * 获取商品列表
	 * @param category
	 * @param list
	 * @return
	 */
	protected ItemCategory getCategoryProductList(ProductCategory category, List<Product> list) {
		
		if(list == null || list.size() == 0) return null;
		
		List<Item> itemList = new ArrayList<Item>();
		
		for(Product product : list) {
			Item item = new Item();
			item.setCode(product.getId());
			item.setTitle(product.getName());
			item.setType(3);
			item.setImageurl(product.getImage());
			itemList.add(item);
		}
		
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setCode(category.getId());
		itemCategory.setShowmore(0);
		itemCategory.setShowtype(3);
		itemCategory.setTitle(category.getName());
		itemCategory.setDataList(itemList);
		
		return itemCategory;
		
	}
	
}
