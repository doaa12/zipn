package cn.bmwm.modules.shop.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ShopService;

/**
 * Controller -- 店铺管理
 * @author zby
 * 2014-8-24 下午9:18:08
 */
@Controller("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController extends BaseController {

	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	
	/**
	 * 列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long productCategoryId, String city, Boolean isTop, Boolean isList, Pageable pageable, ModelMap model) {
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		model.addAttribute("productCategoryId", productCategoryId);
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("city", city);
		model.addAttribute("isTop", isTop);
		model.addAttribute("isList", isList);
		model.addAttribute("cities", shopService.findAllCities());
		model.addAttribute("page", shopService.findPage(productCategory, city, isTop, isList, pageable));
		return "/admin/shop/list";
	}
	
}
