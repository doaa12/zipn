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
import cn.bmwm.modules.shop.controller.app.vo.ItemShop;
import cn.bmwm.modules.shop.controller.app.vo.ShopDetail;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopCategory;
import cn.bmwm.modules.shop.entity.ShopImage;
import cn.bmwm.modules.shop.entity.ShopReview;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ProductService;
import cn.bmwm.modules.shop.service.PromotionService;
import cn.bmwm.modules.shop.service.ShopCategoryService;
import cn.bmwm.modules.shop.service.ShopReviewService;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.sys.exception.BusinessException;

/**
 * App - 店铺
 * @author zhoupuyue
 * @date 2014-8-28
 */
@Controller("appShopController")
@RequestMapping(value = "/app/shop")
public class ShopController extends AppBaseController {
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	@Resource(name = "shopCategoryServiceImpl")
	private ShopCategoryService shopCategoryService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "shopReviewServiceImpl")
	private ShopReviewService shopReviewService;
	
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	
	
	/**
	 * 店铺主页
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> index(Long id) {
		
		if(id == null) {
			throw new BusinessException(" Parameter 'id' can not be null ! ");
		}
		
		Shop shop = shopService.find(id);
		
		if(shop == null) {
			throw new BusinessException(" Invalid parameter 'id' ! ");
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", getShopDetail(shop));
		result.put("categories", shopCategoryService.findAllShopCategories(shop));
		
		return result;
		
	}
	

	/**
	 * 店铺列表
	 * 首页和一级分类下的商铺推荐,点击更多,显示该分类下的店铺列表
	 * @param order : 排序，1：推荐，2：人气，3：附近，4：价格
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> list(Long catId, String city, Integer order, Integer page, Integer size, Integer x, Integer y) {
		
		if(catId == null) {
			throw new BusinessException(" Parameter 'catId' can not be null ! ");
		}
		
		if(city == null || city.trim().equals("")) {
			throw new BusinessException(" Parameter 'city' can not be empty ! ");
		}
		
		ProductCategory category = productCategoryService.find(catId);
		
		if(category == null) {
			throw new BusinessException(" Invalid Parameter 'catId' ! ");
		}
		
		if(page == null) page = 1;
		if(size == null) size = 10;
		if(order == null) order = 1;
		
		ItemPage<Shop> itemPage = shopService.findList(city, category, page, size, order, x, y);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", getShopItems(itemPage.getList()));
		
		return result;
		
	}
	
	/**
	 * 店铺内商品列表
	 * @param shopId：店铺ID
	 * @param type：列表类型，1：所有商品，2：促销商品，3：店铺分类
	 * @param catId：店铺分类
	 * @param page：页码
	 * @param size：每页显示商品数量
	 * @param order：排序方式，1：推荐，2：人气，3：距离，4：价格
	 * @param x：纬度
	 * @param y：经度
	 * @return
	 */
	@RequestMapping(value = "/productlist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> productList(Long shopId, Integer type, Long catId, Integer page, Integer size, Integer order, Integer x, Integer y) {
		
		if(type == 3 && catId == null) {
			throw new BusinessException(" Parameter 'catId' can not be null !");
		}
		
		if(page == null) page = 1;
		if(size == null) size = 10;
		if(type == null) type = 1;
		if(order == null) order = 2;
		
		if(order == 3 && (x == null || y == null)){
			throw new BusinessException(" Parameter 'x' and 'y' can not be null ! ");
		}
		
		Shop shop = shopService.find(shopId);
		
		if(shop == null) {
			throw new BusinessException(" Invalid parameter 'shopId' ! ");
		}
		
		ShopCategory category = null;
		
		if(catId != null){
			category = shopCategoryService.find(catId);
		}
		
		if(type == 3 && category == null) {
			throw new BusinessException(" Invalid parameter 'catId' ! ");
		}
		
		ItemPage<Product> itemPage = productService.findShopProductList(shop, type, category, page, size, order, x, y);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", getProductItems(itemPage.getList()));
		
		return result;
		
	}
	
	/**
	 * 获取店铺列表
	 * @param shopList
	 * @return
	 */
	public List<ItemShop> getShopItems(List<Shop> shopList) {
		
		List<ItemShop> list = new ArrayList<ItemShop>();
		
		if(shopList == null || shopList.size() == 0 ) return list;
		
		for(Shop shop : shopList) {
			
			ItemShop item = new ItemShop();
			item.setCode(shop.getId());
			item.setTitle(shop.getName());
			item.setDistance("300米");
			item.setPrice(shop.getAvgPrice());
			item.setScore(shop.getAvgScore());
			item.setStatus(shop.getStatus());
			item.setCategory(shop.getProductCategory().getName());
			item.setArea(shop.getArea().getName());
			item.setImageurl(shop.getImage());
			
			if(shop.getShopType() != null) {
				item.setType(shop.getShopType());
			}
			
			list.add(item);
			
		}
		
		return list;
		
	}
	
	/**
	 * 获取店铺详情
	 * @param shop
	 * @return
	 */
	public ShopDetail getShopDetail(Shop shop) {
		
		ShopDetail detail = new ShopDetail();
		
		detail.setCode(shop.getId());
		detail.setTitle(shop.getName());
		detail.setHeaderImageurl(shop.getLogo());
		detail.setScore(shop.getAvgScore());
		detail.setCollectFlag(0);
		detail.setAllProduct(productService.findShopProductCount(shop));
		detail.setSaleProduct(promotionService.findShopPromotionCount(shop));
		detail.setSaleStatus(shop.getStatus());
		detail.setDesc(shop.getDescription());
		detail.setType(shop.getShopType());
		detail.setAddress(shop.getAddress());
		detail.setNotice(shop.getNotice());
		
		List<ShopImage> shopImages = shop.getShopImages();
		List<String> imageList = new ArrayList<String>();
		
		if(shopImages != null && shopImages.size() > 0) {
			for(ShopImage image : shopImages) {
				imageList.add(image.getMedium());
			}
		}
		
		detail.setShopImages(imageList);
		
		ShopReview shopReview = shopReviewService.findLatestReview(shop);
		detail.setEvaluate(shopReview == null ? "" : shopReview.getContent());
		
		//TODO:添加店铺活动
		
		List<Product> recommendList = productService.findShopRecommendList(shop);
		
		detail.setRecommend(this.getProductItemCategory(shop.getProductCategory(), recommendList));
		
		return detail;
		
	}
	
}
