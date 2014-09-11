/**
 * 
 */
package cn.bmwm.modules.shop.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.bmwm.modules.shop.entity.Area;
import cn.bmwm.modules.shop.entity.VirtualShopCategory;
import cn.bmwm.modules.shop.service.AreaService;
import cn.bmwm.modules.shop.service.VirtualShopCategoryService;

/**
 * 店铺虚拟分类
 * @author zhoupuyue
 * @date 2014-9-11
 */
@Controller("adminVirtualShopCategoryController")
@RequestMapping("/admin/virtual_shop_category")
public class VirtualShopCategoryController extends BaseController {

	@Resource(name = "virtualShopCategoryServiceImpl")
	private VirtualShopCategoryService virtualShopCategoryService;
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String city, ModelMap model) {
		model.addAttribute("virtualCategories", virtualShopCategoryService.findList(city));
		return "/admin/virtual_shop_category/list";
	}
	
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("provinces", areaService.findRoots());
		return "/admin/virtual_shop_category/add";
	}
	
	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("virtualCategory", virtualShopCategoryService.find(id));
		return "/admin/virtual_shop_category/edit";
	}
	
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(VirtualShopCategory category, Long provinceId, Long cityId, Long areaId, ModelMap model, RedirectAttributes redirectAttributes) {
		
		if(areaId != null && areaId != 0) {
			Area area = areaService.find(areaId);
			category.setCity(area.getFullName());
			category.setArea(area);
		}else if(cityId != null && cityId != 0){
			Area city = areaService.find(cityId);
			category.setCity(city.getFullName());
			category.setArea(city);
		}else{
			Area province = areaService.find(provinceId);
			category.setCity(province.getFullName());
			category.setArea(province);
		}
		
		if(!isValid(category)) {
			return ERROR_VIEW;
		}
		
		virtualShopCategoryService.save(category);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}
	
	/**
	 * 更新
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(VirtualShopCategory category, ModelMap model, RedirectAttributes redirectAttributes) {
		
		VirtualShopCategory pcategory = virtualShopCategoryService.find(category.getId());
		
		pcategory.setName(category.getName());
		pcategory.setOrder(category.getOrder());
		
		if(!isValid(pcategory)) {
			return ERROR_VIEW;
		}
		
		virtualShopCategoryService.update(pcategory);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Long[] ids, ModelMap model, RedirectAttributes redirectAttributes) {
		virtualShopCategoryService.delete(ids);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
}
