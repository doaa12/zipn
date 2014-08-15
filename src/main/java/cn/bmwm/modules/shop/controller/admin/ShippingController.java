/*


 * */
package cn.bmwm.modules.shop.controller.admin;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.common.utils.Message;
import cn.bmwm.modules.shop.service.ShippingService;

/**
 * Controller - 发货单
 * 
 *
 * @version 1.0
 */
@Controller("adminShippingController")
@RequestMapping("/admin/shipping")
public class ShippingController extends BaseController {

	@Resource(name = "shippingServiceImpl")
	private ShippingService shippingService;

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		model.addAttribute("shipping", shippingService.find(id));
		return "/admin/shipping/view";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", shippingService.findPage(pageable));
		return "/admin/shipping/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		shippingService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}