/*


 * */
package cn.bmwm.modules.shop.controller.shopadmin;

import java.util.Iterator;

import javax.annotation.Resource;

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
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.Specification;
import cn.bmwm.modules.shop.entity.Specification.Type;
import cn.bmwm.modules.shop.entity.SpecificationValue;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.shop.service.SpecificationService;
import cn.bmwm.modules.sys.security.Principal;

/**
 * Controller - 规格
 * 
 *
 * @version 1.0
 */
@Controller("shopadminSpecificationController")
@RequestMapping("/shopadmin/specification")
public class SpecificationController extends BaseController {

	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("types", Type.values());
		return "/shopadmin/specification/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Specification specification, RedirectAttributes redirectAttributes) {
		
		for (Iterator<SpecificationValue> iterator = specification.getSpecificationValues().iterator(); iterator.hasNext();) {
			SpecificationValue specificationValue = iterator.next();
			if (specificationValue == null || specificationValue.getName() == null) {
				iterator.remove();
			} else {
				if (specification.getType() == Type.text) {
					specificationValue.setImage(null);
				}
				specificationValue.setSpecification(specification);
			}
		}
		
		if (!isValid(specification)) {
			return ERROR_VIEW;
		}
		
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		
		//specification.setProductSpecificationValues(null);
		specification.setShop(shop);
		
		specificationService.save(specification);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		
		model.addAttribute("types", Type.values());
		model.addAttribute("specification", specificationService.find(id));
		
		return "/shopadmin/specification/edit";
		
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Specification specification, RedirectAttributes redirectAttributes) {
		
		for (Iterator<SpecificationValue> iterator = specification.getSpecificationValues().iterator(); iterator.hasNext();) {
			SpecificationValue specificationValue = iterator.next();
			if (specificationValue == null || specificationValue.getName() == null) {
				iterator.remove();
			} else {
				if (specification.getType() == Type.text) {
					specificationValue.setImage(null);
				}
				specificationValue.setSpecification(specification);
			}
		}
		
		if (!isValid(specification)) {
			return ERROR_VIEW;
		}
		
		specificationService.update(specification, "products", "shop");
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
		
		model.addAttribute("page", specificationService.findPage(shop, pageable));
		
		return "/shopadmin/specification/list";
		
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		
		if (ids != null) {
			for (Long id : ids) {
				Specification specification = specificationService.find(id);
				/*
				if (specification != null && specification.getProductSpecificationValues() != null && !specification.getProductSpecificationValues().isEmpty()) {
					return Message.error("admin.specification.deleteExistProductNotAllowed", specification.getName());
				}
				*/
			}
			specificationService.delete(ids);
		}
		
		return SUCCESS_MESSAGE;
		
	}

}