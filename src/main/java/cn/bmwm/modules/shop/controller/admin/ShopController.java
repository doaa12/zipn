package cn.bmwm.modules.shop.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.Admin;
import cn.bmwm.modules.shop.entity.Area;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.VirtualShopCategory;
import cn.bmwm.modules.shop.service.AdminService;
import cn.bmwm.modules.shop.service.AreaService;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.shop.service.VirtualShopCategoryService;

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
	
	@Resource(name = "virtualShopCategoryServiceImpl")
	private VirtualShopCategoryService virtualShopCategoryService;
	
	
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
		model.addAttribute("admins", adminService.findFreeAdmins());
		model.addAttribute("virtualCategories", virtualShopCategoryService.findList(""));
		return "/admin/shop/add";
	}
	
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Shop shop, Long productCategoryId, Long provinceId, Long cityId, Long areaId, Long adminId, Long[] virtualCategories, RedirectAttributes redirectAttributes) {
		
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		shop.setProductCategory(productCategory);
		shop.setTreePath(productCategory.getTreePath() + productCategory.getId() + ",");
		shop.setShopType(productCategory.getName());
		shop.setPayAccount("");
		
		if(areaId != null && areaId != 0) {
			Area area = areaService.find(areaId);
			shop.setCity(area.getFullName());
			shop.setRegion(area.getName());
			shop.setArea(area);
		}else if(cityId != null && cityId != 0){
			Area city = areaService.find(cityId);
			shop.setCity(city.getFullName());
			shop.setRegion(city.getName());
			shop.setArea(city);
		}else{
			Area province = areaService.find(provinceId);
			shop.setCity(province.getFullName());
			shop.setRegion(province.getName());
			shop.setArea(province);
		}
		
		if(virtualCategories != null && virtualCategories.length > 0) {
			List<VirtualShopCategory> virtualCategoryList = new ArrayList<VirtualShopCategory>();
			for(Long catId : virtualCategories) {
				VirtualShopCategory category = virtualShopCategoryService.find(catId);
				virtualCategoryList.add(category);
			}
			shop.setVirtualCategories(virtualCategoryList);
		}
		
		Admin admin = adminService.find(adminId);
		shop.setAdmin(admin);
		shop.setStatus(1);
		
		if (!isValid(shop)) {
			return ERROR_VIEW;
		}
		
		shopService.save(shop);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
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
		model.addAttribute("admins", adminService.findFreeAdmins());
		
		List<VirtualShopCategory> categories = virtualShopCategoryService.findList("");
		List<VirtualShopCategory> shopCategories = shop.getVirtualCategories();
		
		if(categories != null && categories.size() > 0) {
			if(shopCategories == null || shopCategories.size() == 0){
				model.addAttribute("virtualCategories", categories);
			}else{
				for(VirtualShopCategory category : categories) {
					for(VirtualShopCategory shopCategory : shopCategories) {
						if(category.getId() == shopCategory.getId()) {
							category.setIsSelected(true);
						}
					}
				}
				model.addAttribute("virtualCategories", categories);
			}
		}
		
		return "/admin/shop/edit";
		
	}
	
	/**
	 * 更新
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Long shopId, Long adminId, Boolean isList, Boolean isTop, ModelMap model, Long[] virtualCategories, RedirectAttributes redirectAttributes) {
		
		Admin admin = adminService.find(adminId);
		
		Shop shop = shopService.find(shopId);
		shop.setAdmin(admin);
		
		if(isList == null) {
			isList = false;
		}
		if(isTop == null) {
			isTop = false;
		}
		
		//TODO:将所有店铺商品更新为下架,删除商品Lucene索引数据,删除店铺Lucene索引数据,反向逻辑也要更新
		if(isList.booleanValue() != shop.getIsList().booleanValue()){
			Set<Product> products = shop.getProducts();
			for(Product product : products) {
				product.setIsList(isList);
			}
		}
		
		if(virtualCategories != null && virtualCategories.length > 0) {
			List<VirtualShopCategory> virtualCategoryList = new ArrayList<VirtualShopCategory>();
			for(Long catId : virtualCategories) {
				VirtualShopCategory category = virtualShopCategoryService.find(catId);
				virtualCategoryList.add(category);
			}
			shop.setVirtualCategories(virtualCategoryList);
		}
		
		shop.setIsList(isList);
		shop.setIsTop(isTop);
		
		shopService.update(shop);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}
	
}
