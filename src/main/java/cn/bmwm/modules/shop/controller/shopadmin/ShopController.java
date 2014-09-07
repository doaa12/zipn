package cn.bmwm.modules.shop.controller.shopadmin;

import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.bmwm.common.utils.FileInfo.FileType;
import cn.bmwm.common.utils.Message;
import cn.bmwm.modules.shop.controller.admin.BaseController;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopImage;
import cn.bmwm.modules.shop.service.FileService;
import cn.bmwm.modules.shop.service.ImageService;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.sys.security.Principal;

/**
 * 商家店铺设置
 * @author zby
 * 2014-9-7 上午9:11:08
 */
@Controller("shopAdminShopController")
@RequestMapping(value = "/shopadmin/shop")
public class ShopController extends BaseController {
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	
	@Resource(name = "imageServiceImpl")
	private ImageService imageService;
	
	/**
	 * 店铺设置
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(ModelMap model) {
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		model.addAttribute("shop", shop);
		return "/shopadmin/shop/setting";
	}
	
	/**
	 * 商家店铺更新
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Shop shop, RedirectAttributes redirectAttributes) {
		
		for (Iterator<ShopImage> iterator = shop.getShopImages().iterator(); iterator.hasNext();) {
			ShopImage shopImage = iterator.next();
			if (shopImage == null || shopImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (shopImage.getFile() != null && !shopImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, shopImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:edit.jhtml";
				}
			}
		}
		
		for (ShopImage shopImage : shop.getShopImages()) {
			imageService.build(shopImage);
		}
		
		Shop pshop = shopService.find(shop.getId());
		
		pshop.setName(shop.getName());
		pshop.setDescription(shop.getDescription());
		pshop.setNotice(shop.getNotice());
		pshop.setAddress(shop.getAddress());
		pshop.setPayAccount(shop.getPayAccount());
		pshop.setImage(shop.getImage());
		pshop.setLogo(shop.getLogo());
		pshop.setStatus(shop.getStatus());
		pshop.setShopImages(shop.getShopImages());
		
		shopService.update(pshop);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:edit.jhtml";
		
	}
	
}
