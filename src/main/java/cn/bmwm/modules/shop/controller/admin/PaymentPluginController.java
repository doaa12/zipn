/*


 * */
package cn.bmwm.modules.shop.controller.admin;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bmwm.modules.shop.service.PluginService;

/**
 * Controller - 支付插件
 * 
 *
 * @version 1.0
 */
@Controller("adminPaymentPluginController")
@RequestMapping("/admin/payment_plugin")
public class PaymentPluginController extends BaseController {

	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("paymentPlugins", pluginService.getPaymentPlugins());
		return "/admin/payment_plugin/list";
	}

}