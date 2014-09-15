package cn.bmwm.modules.shop.controller.shopadmin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.bmwm.common.persistence.Order;
import cn.bmwm.common.utils.Message;
import cn.bmwm.modules.shop.controller.admin.BaseController;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopActivity;
import cn.bmwm.modules.shop.service.ShopActivityService;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.sys.exception.BusinessException;
import cn.bmwm.modules.sys.security.Principal;

/**
 * Controller -- App广告管理
 * @author zby
 * 2014-9-13 上午9:16:21
 */
@Controller("shopAdminShopActivityController")
@RequestMapping("/shopadmin/activity")
public class ShopActivityController extends BaseController {
	
	@Resource(name = "shopActivityServiceImpl")
	private ShopActivityService shopActivityService;
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	
	/**
	 * 列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		
		Order order = Order.asc("order");
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		
		model.addAttribute("list", shopActivityService.findList(shop, null, orderList));
		
		return "/shopadmin/activity/list";
		
	}
	
	/**
	 * 添加
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/shopadmin/activity/add";
	}
	
	/**
	 * 编辑
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		
		ShopActivity shopActivity = shopActivityService.find(id);
		
		if(shopActivity == null) {
			throw new BusinessException("非法的参数'id'！");
		}
		
		model.addAttribute("activity", shopActivity);
		
		return "/shopadmin/activity/edit";
		
	}
	
	/**
	 * 保存
	 * @param shopActivity
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ShopActivity shopActivity, RedirectAttributes redirectAttributes) {
		
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		shopActivity.setShop(shop);
		
		shopActivityService.save(shopActivity);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}
	
	/**
	 * 更新
	 * @param shopActivity
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ShopActivity shopActivity, RedirectAttributes redirectAttributes) {
		
		ShopActivity activity = shopActivityService.find(shopActivity.getId());
		
		activity.setImageurl(shopActivity.getImageurl());
		activity.setLinkurl(shopActivity.getLinkurl());
		activity.setOrder(shopActivity.getOrder());
		
		shopActivityService.update(activity);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}
	
	/**
	 * 删除
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Message delete(Long[] ids, RedirectAttributes redirectAttributes) {
		shopActivityService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
}
