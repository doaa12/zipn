/*


 * */
package cn.bmwm.modules.shop.controller.shopadmin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.common.utils.FreemarkerUtils;
import cn.bmwm.common.utils.Message;
import cn.bmwm.modules.shop.controller.admin.BaseController;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.Promotion;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.ProductService;
import cn.bmwm.modules.shop.service.PromotionService;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.sys.security.Principal;

/**
 * Controller - 促销
 * 
 *
 * @version 1.0
 */
@Controller("shopadminPromotionController")
@RequestMapping("/shopadmin/promotion")
public class PromotionController extends BaseController {

	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;

	/**
	 * 检查价格运算表达式是否正确
	 */
	@RequestMapping(value = "/check_price_expression", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkPriceExpression(String priceExpression) {
		if (StringUtils.isEmpty(priceExpression)) {
			return false;
		}
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("quantity", 111);
			model.put("price", new BigDecimal(9.99));
			new BigDecimal(FreemarkerUtils.process("${(" + priceExpression + ")?string(\"0.######\")}", model));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 检查积分运算表达式是否正确
	 */
	@RequestMapping(value = "/check_point_expression", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkPointExpression(String pointExpression) {
		if (StringUtils.isEmpty(pointExpression)) {
			return false;
		}
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("quantity", 111);
			model.put("point", 999L);
			Long.valueOf(FreemarkerUtils.process("${(" + pointExpression + ")?string(\"0.######\")}", model));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 商品选择
	 */
	@RequestMapping(value = "/product_select", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> productSelect(String q) {
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<Product> products = productService.search(shop, q, false, 20);
			for (Product product : products) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", product.getId());
				map.put("sn", product.getSn());
				map.put("fullName", product.getFullName());
				map.put("path", product.getPath());
				data.add(map);
			}
		}
		return data;
	}

	/**
	 * 赠品选择
	 */
	@RequestMapping(value = "/gift_select", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> giftSelect(String q) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<Product> products = productService.search(q, true, 20);
			for (Product product : products) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", product.getId());
				map.put("sn", product.getSn());
				map.put("fullName", product.getFullName());
				map.put("path", product.getPath());
				data.add(map);
			}
		}
		return data;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		//model.addAttribute("memberRanks", memberRankService.findAll());
		//model.addAttribute("productCategories", productCategoryService.findAll());
		//model.addAttribute("brands", brandService.findAll());
		//model.addAttribute("coupons", couponService.findAll());
		return "/shopadmin/promotion/add";
	}

	/**
	 * 保存
	 */
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Promotion promotion, Long[] productIds, RedirectAttributes redirectAttributes) {
		
		//promotion.setMemberRanks(new HashSet<MemberRank>(memberRankService.findList(memberRankIds)));
		//promotion.setProductCategories(new HashSet<ProductCategory>(productCategoryService.findList(productCategoryIds)));
		//promotion.setBrands(new HashSet<Brand>(brandService.findList(brandIds)));
		//promotion.setCoupons(new HashSet<Coupon>(couponService.findList(couponIds)));
		
		if(promotion.getType() == 1) {
			for (Product product : productService.findList(productIds)) {
				if (!product.getIsGift()) {
					promotion.getProducts().add(product);
				}
			}
		}
		
		/*
		for (Iterator<GiftItem> iterator = promotion.getGiftItems().iterator(); iterator.hasNext();) {
			GiftItem giftItem = iterator.next();
			if (giftItem == null || giftItem.getGift() == null || giftItem.getGift().getId() == null) {
				iterator.remove();
			} else {
				giftItem.setGift(productService.find(giftItem.getGift().getId()));
				giftItem.setPromotion(promotion);
			}
		}
		*/
		
		if (!isValid(promotion)) {
			return ERROR_VIEW;
		}
		
		if (promotion.getBeginDate() != null && promotion.getEndDate() != null && promotion.getBeginDate().after(promotion.getEndDate())) {
			return ERROR_VIEW;
		}
		
		if (promotion.getMinimumQuantity() != null && promotion.getMaximumQuantity() != null && promotion.getMinimumQuantity() > promotion.getMaximumQuantity()) {
			return ERROR_VIEW;
		}
		
		if (promotion.getMinimumPrice() != null && promotion.getMaximumPrice() != null && promotion.getMinimumPrice().compareTo(promotion.getMaximumPrice()) > 0) {
			return ERROR_VIEW;
		}
		
		if (StringUtils.isNotEmpty(promotion.getPriceExpression())) {
			try {
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("quantity", 111);
				model.put("price", new BigDecimal(9.99));
				new BigDecimal(FreemarkerUtils.process("${(" + promotion.getPriceExpression() + ")?string(\"0.######\")}", model));
			} catch (Exception e) {
				return ERROR_VIEW;
			}
		}
		
		/*
		if (StringUtils.isNotEmpty(promotion.getPointExpression())) {
			try {
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("quantity", 111);
				model.put("point", 999L);
				Long.valueOf(FreemarkerUtils.process("${(" + promotion.getPointExpression() + ")?string(\"0.######\")}", model));
			} catch (Exception e) {
				return ERROR_VIEW;
			}
		}
		*/
		
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		
		promotion.setShop(shop);
		
		promotionService.save(promotion);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}
	

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("promotion", promotionService.find(id));
		//model.addAttribute("memberRanks", memberRankService.findAll());
		//model.addAttribute("productCategories", productCategoryService.findAll());
		//model.addAttribute("brands", brandService.findAll());
		//model.addAttribute("coupons", couponService.findAll());
		return "/shopadmin/promotion/edit";
	}

	/**
	 * 更新
	 */
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Promotion promotion, Long[] productIds, RedirectAttributes redirectAttributes) {
		
		//promotion.setMemberRanks(new HashSet<MemberRank>(memberRankService.findList(memberRankIds)));
		//promotion.setProductCategories(new HashSet<ProductCategory>(productCategoryService.findList(productCategoryIds)));
		//promotion.setBrands(new HashSet<Brand>(brandService.findList(brandIds)));
		//promotion.setCoupons(new HashSet<Coupon>(couponService.findList(couponIds)));
		
		if(promotion.getType() == 1) {
			for (Product product : productService.findList(productIds)) {
				if (!product.getIsGift()) {
					promotion.getProducts().add(product);
				}
			}
		}
		
		/*
		for (Iterator<GiftItem> iterator = promotion.getGiftItems().iterator(); iterator.hasNext();) {
			GiftItem giftItem = iterator.next();
			if (giftItem == null || giftItem.getGift() == null || giftItem.getGift().getId() == null) {
				iterator.remove();
			} else {
				giftItem.setGift(productService.find(giftItem.getGift().getId()));
				giftItem.setPromotion(promotion);
			}
		}
		*/
		
		if (promotion.getBeginDate() != null && promotion.getEndDate() != null && promotion.getBeginDate().after(promotion.getEndDate())) {
			return ERROR_VIEW;
		}
		
		if (promotion.getMinimumQuantity() != null && promotion.getMaximumQuantity() != null && promotion.getMinimumQuantity() > promotion.getMaximumQuantity()) {
			return ERROR_VIEW;
		}
		
		if (promotion.getMinimumPrice() != null && promotion.getMaximumPrice() != null && promotion.getMinimumPrice().compareTo(promotion.getMaximumPrice()) > 0) {
			return ERROR_VIEW;
		}
		
		if (StringUtils.isNotEmpty(promotion.getPriceExpression())) {
			try {
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("quantity", 111);
				model.put("price", new BigDecimal(9.99));
				new BigDecimal(FreemarkerUtils.process("${(" + promotion.getPriceExpression() + ")?string(\"0.######\")}", model));
			} catch (Exception e) {
				return ERROR_VIEW;
			}
		}
		
		/*
		if (StringUtils.isNotEmpty(promotion.getPointExpression())) {
			try {
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("quantity", 111);
				model.put("point", 999L);
				Long.valueOf(FreemarkerUtils.process("${(" + promotion.getPointExpression() + ")?string(\"0.######\")}", model));
			} catch (Exception e) {
				return ERROR_VIEW;
			}
		}
		*/
		
		Promotion ppromotion = promotionService.find(promotion.getId());
		
		BeanUtils.copyProperties(promotion, ppromotion, new String[]{"id", "shop"});
		
		promotionService.update(ppromotion);
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
		model.addAttribute("page", promotionService.findPage(shop, pageable));
		return "/shopadmin/promotion/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		promotionService.delete(shop, ids);
		return SUCCESS_MESSAGE;
	}

}