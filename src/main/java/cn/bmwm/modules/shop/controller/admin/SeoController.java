/*


 * */
package cn.bmwm.modules.shop.controller.admin;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.Seo;
import cn.bmwm.modules.shop.service.SeoService;

/**
 * Controller - SEO设置
 * 
 *
 * @version 1.0
 */
@Controller("adminSeoController")
@RequestMapping("/admin/seo")
public class SeoController extends BaseController {

	@Resource(name = "seoServiceImpl")
	private SeoService seoService;

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("seo", seoService.find(id));
		return "/admin/seo/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Seo seo, RedirectAttributes redirectAttributes) {
		if (!isValid(seo)) {
			return ERROR_VIEW;
		}
		seoService.update(seo, "type");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", seoService.findPage(pageable));
		return "/admin/seo/list";
	}

}