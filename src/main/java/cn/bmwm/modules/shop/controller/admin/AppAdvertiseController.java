package cn.bmwm.modules.shop.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.bmwm.modules.shop.entity.AppAdvertise;
import cn.bmwm.modules.shop.entity.Area;
import cn.bmwm.modules.shop.service.AppAdvertiseService;
import cn.bmwm.modules.shop.service.AreaService;
import cn.bmwm.modules.sys.exception.BusinessException;

/**
 * Controller -- App广告管理
 * @author zby
 * 2014-9-13 上午9:16:21
 */
@Controller("adminAppAdvertiseController")
@RequestMapping("/admin/app_ad")
public class AppAdvertiseController extends BaseController {
	
	@Resource(name = "appAdvertiseServiceImpl")
	private AppAdvertiseService appAdvertiseService;
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	
	/**
	 * 列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Boolean isEnabled, ModelMap model) {
		model.addAttribute("isEnabled", isEnabled);
		model.addAttribute("list", appAdvertiseService.findList(null, null, null));
		return "/admin/app_ad/list";
	}
	
	/**
	 * 添加
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("provinces", areaService.findRoots());
		return "/admin/app_ad/add";
	}
	
	/**
	 * 编辑
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		
		AppAdvertise appAdvertise = appAdvertiseService.find(id);
		
		if(appAdvertise == null) {
			throw new BusinessException("非法的参数'id'！");
		}
		
		Area area = appAdvertise.getArea();
		
		Area city = area.getParent();
		
		if(city == null) {
			model.addAttribute("province", area);
		}else {
			Area province = city.getParent();
			if(province == null) {
				model.addAttribute("province", city);
				model.addAttribute("city", area);
			}else{
				model.addAttribute("province", province);
				model.addAttribute("city", city);
				model.addAttribute("area", area);
			}
		}
		
		model.addAttribute("provinces", areaService.findRoots());
		model.addAttribute("ad", appAdvertise);
		
		return "/admin/app_ad/edit";
		
	}
	
	/**
	 * 保存
	 * @param appAdvertise
	 * @param provinceId
	 * @param cityId
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(AppAdvertise appAdvertise, Long provinceId, Long cityId, Long areaId, RedirectAttributes redirectAttributes) {
		
		if(provinceId == null) {
			throw new BusinessException("请选择城市！");
		}
		
		if(areaId != null && areaId != 0) {
			Area area = areaService.find(areaId);
			appAdvertise.setCity(area.getFullName());
			appAdvertise.setArea(area);
		}else if(cityId != null && cityId != 0){
			Area city = areaService.find(cityId);
			appAdvertise.setCity(city.getFullName());
			appAdvertise.setArea(city);
		}else{
			Area province = areaService.find(provinceId);
			appAdvertise.setCity(province.getFullName());
			appAdvertise.setArea(province);
		}
		
		appAdvertiseService.save(appAdvertise);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}
	
	/**
	 * 更新
	 * @param appAdvertise
	 * @param provinceId
	 * @param cityId
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(AppAdvertise appAdvertise, Long provinceId, Long cityId, Long areaId, RedirectAttributes redirectAttributes) {
		
		if(provinceId == null) {
			throw new BusinessException("请选择城市！");
		}
		
		if(areaId != null && areaId != 0) {
			Area area = areaService.find(areaId);
			appAdvertise.setCity(area.getFullName());
			appAdvertise.setArea(area);
		}else if(cityId != null && cityId != 0){
			Area city = areaService.find(cityId);
			appAdvertise.setCity(city.getFullName());
			appAdvertise.setArea(city);
		}else{
			Area province = areaService.find(provinceId);
			appAdvertise.setCity(province.getFullName());
			appAdvertise.setArea(province);
		}
		
		AppAdvertise ad = appAdvertiseService.find(appAdvertise.getId());
		
		ad.setArea(appAdvertise.getArea());
		ad.setCity(appAdvertise.getCity());
		ad.setDescription(appAdvertise.getDescription());
		ad.setImageUrl(appAdvertise.getImageUrl());
		ad.setLinkUrl(appAdvertise.getLinkUrl());
		ad.setIsEnabled(appAdvertise.getIsEnabled());
		
		appAdvertiseService.update(ad);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}
	
}
