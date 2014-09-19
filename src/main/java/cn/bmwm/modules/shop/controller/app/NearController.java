package cn.bmwm.modules.shop.controller.app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.modules.shop.controller.app.vo.ItemPage;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.sys.exception.BusinessException;

/**
 * App -- 附近
 * @author zby
 * 2014-9-17 下午11:33:26
 */
@Controller("appNearControler")
@RequestMapping(value = "/app/near")
public class NearController extends AppBaseController {
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;

	/**
	 * 店铺列表
	 * 首页和一级分类下的商铺推荐,点击更多,显示该分类下的店铺列表
	 * @param order : 排序，1：推荐，2：人气，3：距离，4：价格
	 * @param catId：类目ID
	 * @param x : 经度
	 * @param y : 纬度
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> list(String city, Integer catId, Integer order, Integer page, Integer size, Double x, Double y) {
		
		if(city == null || city.trim().equals("")) {
			throw new BusinessException(" Parameter 'city' can not be empty ! ");
		}
		
		if(page == null) page = 1;
		if(size == null) size = 10;
		if(order == null) order = 1;
		
		if(order == 3 && (x == null || y == null)) {
			throw new BusinessException(" Parameter 'x' or 'y' can not be null ! ");
		}
		
		BigDecimal decimalx = null;
		BigDecimal decimaly = null;
		
		if(x != null) {
			decimalx = new BigDecimal(x);
		}
		
		if(y != null) {
			decimaly = new BigDecimal(y);
		}
		
		ItemPage<Shop> itemPage = shopService.findList(city, catId, page, size, order, decimalx, decimaly);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", getShopItems(itemPage.getList(), x, y));
		result.put("categories", productCategoryService.findRoots());
		
		return result;
		
	}
	
}
