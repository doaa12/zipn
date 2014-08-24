/*


 * */
package cn.bmwm.modules.shop.controller.shopadmin;

import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.common.utils.Message;
import cn.bmwm.modules.shop.controller.admin.BaseController;
import cn.bmwm.modules.shop.entity.Attribute;
import cn.bmwm.modules.shop.entity.BaseEntity.Save;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.AttributeService;
import cn.bmwm.modules.shop.service.ShopCategoryService;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.sys.security.Principal;

/**
 * Controller - 属性
 * 
 *
 * @version 1.0
 */
@Controller("shopadminAttributeController")
@RequestMapping("/shopadmin/attribute")
public class AttributeController extends BaseController {

	@Resource(name = "attributeServiceImpl")
	private AttributeService attributeService;
	
	@Resource(name = "shopCategoryServiceImpl")
	private ShopCategoryService shopCategoryService;
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		model.addAttribute("shopCategories", shop.getShopCategories());
		model.addAttribute("attributeValuePropertyCount", Product.ATTRIBUTE_VALUE_PROPERTY_COUNT);
		return "/shopadmin/attribute/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Attribute attribute, Long shopCategoryId, RedirectAttributes redirectAttributes) {
		
		for (Iterator<String> iterator = attribute.getOptions().iterator(); iterator.hasNext();) {
			String option = iterator.next();
			if (StringUtils.isEmpty(option)) {
				iterator.remove();
			}
		}
		
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		
		attribute.setShopCategory(shopCategoryService.find(shopCategoryId));
		attribute.setShop(shop);
		
		if (!isValid(attribute, Save.class)) {
			return ERROR_VIEW;
		}
		
		if (attribute.getShopCategory().getAttributes().size() >= Product.ATTRIBUTE_VALUE_PROPERTY_COUNT) {
			addFlashMessage(redirectAttributes, Message.error("admin.attribute.addCountNotAllowed", Product.ATTRIBUTE_VALUE_PROPERTY_COUNT));
		} else {
			attribute.setPropertyIndex(null);
			attributeService.save(attribute);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		}
		
		return "redirect:list.jhtml";
		
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("attributeValuePropertyCount", Product.ATTRIBUTE_VALUE_PROPERTY_COUNT);
		model.addAttribute("attribute", attributeService.find(id));
		return "/shopadmin/attribute/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Attribute attribute, RedirectAttributes redirectAttributes) {
		
		for (Iterator<String> iterator = attribute.getOptions().iterator(); iterator.hasNext();) {
			String option = iterator.next();
			if (StringUtils.isEmpty(option)) {
				iterator.remove();
			}
		}
		
		if (!isValid(attribute)) {
			return ERROR_VIEW;
		}
		
		attributeService.update(attribute, "propertyIndex", "shopCategory");
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		model.addAttribute("page", attributeService.findPage(shop, pageable));
		return "/shopadmin/attribute/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		attributeService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}