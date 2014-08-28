package cn.bmwm.modules.shop.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.Admin;
import cn.bmwm.modules.shop.entity.Area;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.AdminService;
import cn.bmwm.modules.shop.service.AreaService;
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
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
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
	
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("provinces", areaService.findRoots());
		model.addAttribute("admins", adminService.findAll());
		return "/admin/shop/add";
	}
	
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Shop shop, Long productCategoryId, Long provinceId, Long cityId, Long adminId, ModelMap model) {
		
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		shop.setProductCategory(productCategory);
		shop.setTreePath(productCategory.getTreePath() + productCategory.getId() + ",");
		
		if(cityId != null){
			Area city = areaService.find(cityId);
			shop.setCity(city.getFullName());
		}else{
			Area province = areaService.find(provinceId);
			shop.setCity(province.getFullName());
		}
		
		Admin admin = adminService.find(adminId);
		shop.setAdmin(admin);
		
		if (!isValid(shop)) {
			return ERROR_VIEW;
		}
		
		shopService.save(shop);
		
		return "redirect:list.jhtml";
		
	}
	
	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		Shop shop = shopService.find(id);
		model.addAttribute("shop", shop);
		model.addAttribute("admins", adminService.findAll());
		return "/admin/shop/edit";
	}
	
	/**
	 * 更新
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Long shopId, Long adminId, Boolean isList, Boolean isTop, ModelMap model) {
		
		Admin admin = adminService.find(adminId);
		
		Shop shop = shopService.find(shopId);
		shop.setAdmin(admin);
		//TODO:是否需要将所有店铺商品更新为下架?
		shop.setIsList(isList);
		shop.setIsTop(isTop);
		
		shopService.update(shop, "productCategory", "name", "city", "address", "payAccount");
		
		return "redirect:list.jhtml";
		
	}
	
}
