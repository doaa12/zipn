/*


 * */
package cn.bmwm.modules.shop.controller.shopadmin;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.bmwm.common.utils.Message;
import cn.bmwm.modules.shop.controller.admin.BaseController;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopCategory;
import cn.bmwm.modules.shop.service.BrandService;
import cn.bmwm.modules.shop.service.ShopCategoryService;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.sys.security.Principal;

/**
 * Controller - 商品分类
 * 
 *
 * @version 1.0
 */
@Controller("shopadminShopCategoryController")
@RequestMapping("/shopadmin/shop_category")
public class ShopCategoryController extends BaseController {
	
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	
	@Resource(name = "shopCategoryServiceImpl")
	private ShopCategoryService shopCategoryService;
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		//model.addAttribute("shop", productCategoryService.findTree());
		model.addAttribute("brands", brandService.findAll());
		return "/shopadmin/shop_category/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ShopCategory shopCategory, Long[] brandIds, RedirectAttributes redirectAttributes) {
		
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		
		Long shopId = principal.getShopId();
		Shop shop = shopService.find(shopId);
		ProductCategory parent = shop.getProductCategory();
		
		shopCategory.setParent(parent);
		//shopCategory.setBrands(new HashSet<Brand>(brandService.findList(brandIds)));
		if (!isValid(shopCategory)) {
			return ERROR_VIEW;
		}
		shopCategory.setTreePath(parent.getTreePath() + parent.getId() + ",");
		//shopCategory.setGrade(null);
		//shopCategory.setChildren(null);
		shopCategory.setParameterGroups(null);
		shopCategory.setAttributes(null);
		shopCategory.setPromotions(null);
		shopCategoryService.save(shopCategory);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		ShopCategory shopCategory = shopCategoryService.find(id);
		model.addAttribute("shopCategories", shopCategoryService.findAll());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("shopCategory", shopCategory);
		return "/shopadmin/shop_category/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ShopCategory shopCategory, Long[] brandIds, RedirectAttributes redirectAttributes) {
		
		//shopCategory.setBrands(new HashSet<Brand>(brandService.findList(brandIds)));
		
		if (!isValid(shopCategory)) {
			return ERROR_VIEW;
		}
		/*
		if (productCategory.getParent() != null) {
			ProductCategory parent = productCategory.getParent();
			if (parent.equals(productCategory)) {
				return ERROR_VIEW;
			}
			List<ProductCategory> children = productCategoryService.findChildren(parent);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		*/
		
		shopCategoryService.update(shopCategory, "treePath", "grade", "children", "products", "parameterGroups", "attributes", "promotions");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("shopCategories", shopCategoryService.findAll());
		return "/shopadmin/shop_category/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		
		ShopCategory shopCategory = shopCategoryService.find(id);
		
		if (shopCategory == null) {
			return ERROR_MESSAGE;
		}
		/*
		Set<ProductCategory> children = productCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("admin.productCategory.deleteExistChildrenNotAllowed");
		}
		*/
		shopCategoryService.delete(id);
		
		return SUCCESS_MESSAGE;
		
	}

}