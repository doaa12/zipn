package cn.bmwm.modules.shop.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.modules.shop.controller.app.vo.ItemProduct;
import cn.bmwm.modules.shop.controller.app.vo.ItemShop;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.Product.OrderType;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.SearchService;
import cn.bmwm.modules.sys.exception.BusinessException;

/**
 * Controller -- 搜索
 * @author zby
 * 2014-9-13 下午8:34:19
 */
@Controller("appSearchController")
@RequestMapping(value = "/app")
public class SearchController extends AppBaseController {

	@Resource(name = "searchServiceImpl")
	private SearchService searchService;
	
	/**
	 * 商品和店铺搜索
	 * @param city：城市
	 * @param keyword：关键字
	 * @param order：排序，1：商品评分，2：商品销量，3：商品价格，4：店铺，按推荐排序，综合排序
	 * @param page
	 * @param size
	 * @param x
	 * @param y
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> search(String city, String keyword, Integer order, Integer page, Integer size, Double x, Double y) {
		
		if(city == null || city.trim().equals("")) {
			throw new BusinessException(" Parameter 'city' can not be empty ! ");
		}
		
		if(order == 4 && (x == null || y == null)) {
			throw new BusinessException(" Parameter 'x' and 'y' can not be null ! ");
		}
		
		if(order == null) order = 1;
		if(page == null) page = 1;
		if(size == null) size = 10;
		
		OrderType orderType = null;
		
		if(order == 1){
			orderType = OrderType.scoreDesc;
		}else if(order == 2) {
			orderType = OrderType.salesDesc;
		}else if(order == 3) {
			orderType = OrderType.priceAsc;
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("flag", 1);
		result.put("version", 1);
		
		if(order == 4) {
			List<Shop> shops = searchService.search(city, keyword, page, size);
			List<ItemShop> itemList = getShopItems(shops, x, y);
			result.put("dataList", itemList);
		}else {
			List<Product> products = searchService.search(city, keyword, orderType, page, size);
			List<ItemProduct> itemList = getProductItems(products);
			result.put("dataList", itemList);
		}
		
		return result;
		
	}
	
}
