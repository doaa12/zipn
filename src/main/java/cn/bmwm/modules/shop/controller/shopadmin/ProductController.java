/**
 * 
 */
package cn.bmwm.modules.shop.controller.shopadmin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import cn.bmwm.common.utils.FileInfo.FileType;
import cn.bmwm.common.utils.Message;
import cn.bmwm.modules.shop.controller.admin.BaseController;
import cn.bmwm.modules.shop.entity.Attribute;
import cn.bmwm.modules.shop.entity.Brand;
import cn.bmwm.modules.shop.entity.MemberRank;
import cn.bmwm.modules.shop.entity.Parameter;
import cn.bmwm.modules.shop.entity.ParameterGroup;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.Product.OrderType;
import cn.bmwm.modules.shop.entity.ProductImage;
import cn.bmwm.modules.shop.entity.ProductSpecification;
import cn.bmwm.modules.shop.entity.ProductSpecificationValue;
import cn.bmwm.modules.shop.entity.Promotion;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopCategory;
import cn.bmwm.modules.shop.entity.Specification;
import cn.bmwm.modules.shop.entity.SpecificationValue;
import cn.bmwm.modules.shop.entity.Tag;
import cn.bmwm.modules.shop.entity.Tag.Type;
import cn.bmwm.modules.shop.service.BrandService;
import cn.bmwm.modules.shop.service.FileService;
import cn.bmwm.modules.shop.service.ImageService;
import cn.bmwm.modules.shop.service.MemberRankService;
import cn.bmwm.modules.shop.service.ProductService;
import cn.bmwm.modules.shop.service.PromotionService;
import cn.bmwm.modules.shop.service.ShopCategoryService;
import cn.bmwm.modules.shop.service.ShopService;
import cn.bmwm.modules.shop.service.SpecificationService;
import cn.bmwm.modules.shop.service.SpecificationValueService;
import cn.bmwm.modules.shop.service.TagService;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.security.Principal;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * 店铺商品管理
 * @author zhoupuyue
 * @date 2014-8-20
 */
@Controller("shopAdminProductController")
@RequestMapping("/shopadmin/product")
public class ProductController extends BaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	
	@Resource(name = "imageServiceImpl")
	private ImageService imageService;
	
	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	
	@Resource(name = "specificationValueServiceImpl")
	private SpecificationValueService specificationValueService;
	
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	
	@Resource(name = "shopCategoryServiceImpl")
	private ShopCategoryService shopCategoryService;
	
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	
	
	/**
	 * 检查编号是否唯一
	 */
	@RequestMapping(value = "/check_sn", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkSn(String previousSn, String sn) {
		if (StringUtils.isEmpty(sn)) {
			return false;
		}
		if (productService.snUnique(previousSn, sn)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取参数组
	 */
	@RequestMapping(value = "/parameter_groups", method = RequestMethod.GET)
	@ResponseBody
	public Set<ParameterGroup> parameterGroups(Long id) {
		ShopCategory shopCategory = shopCategoryService.find(id);
		return shopCategory.getParameterGroups();
	}
	
	/**
	 * 获取属性
	 */
	@RequestMapping(value = "/attributes", method = RequestMethod.GET)
	@ResponseBody
	public Set<Attribute> attributes(Long id) {
		ShopCategory shopCategory = shopCategoryService.find(id);
		return shopCategory.getAttributes();
	}
	
	/**
	 * 店铺商品列表
	 */
	//zhoupuyue,增加店铺商品列表
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String shoplist(Long shopCategoryId, Long brandId, Long promotionId, Long tagId, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert, Pageable pageable, ModelMap model) {
		
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		ShopCategory shopCategory = shopCategoryService.find(shopCategoryId);
		
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagId);
		model.addAttribute("shopCategories", shop.getShopCategories());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("promotions", promotionService.findAll());
		model.addAttribute("tags", tagService.findList(Type.product));
		model.addAttribute("shopCategoryId", shopCategoryId);
		model.addAttribute("brandId", brandId);
		model.addAttribute("promotionId", promotionId);
		model.addAttribute("tagId", tagId);
		model.addAttribute("isMarketable", isMarketable);
		model.addAttribute("isList", isList);
		model.addAttribute("isTop", isTop);
		model.addAttribute("isGift", isGift);
		model.addAttribute("isOutOfStock", isOutOfStock);
		model.addAttribute("isStockAlert", isStockAlert);
		model.addAttribute("page", productService.findPage(shop, shopCategory, brand, promotion, tags, null, null, null, isMarketable, isList, isTop, isGift, isOutOfStock, isStockAlert, OrderType.dateDesc, pageable));
		
		return "/shopadmin/product/list";
		
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		
		model.addAttribute("shopCategories", shop.getShopCategories());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("tags", tagService.findList(Type.product));
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		
		return "/shopadmin/product/add";
		
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Product product, Long shopCategoryId, Long brandId, Long[] tagIds, Long[] specificationIds, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		ShopCategory shopCategory = shopCategoryService.find(shopCategoryId);
		
		String city = shop.getCity();
		
		for (Iterator<ProductImage> iterator = product.getProductImages().iterator(); iterator.hasNext();) {
			ProductImage productImage = iterator.next();
			if (productImage == null || productImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (productImage.getFile() != null && !productImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, productImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:add.jhtml";
				}
			}
		}
		
		product.setIsTop(false);
		product.setBrand(brandService.find(brandId));
		product.setTags(new HashSet<Tag>(tagService.findList(tagIds)));
		if (!isValid(product)) {
			return ERROR_VIEW;
		}
		if (StringUtils.isNotEmpty(product.getSn()) && productService.snExists(product.getSn())) {
			return ERROR_VIEW;
		}
		if (product.getMarketPrice() == null) {
			BigDecimal defaultMarketPrice = calculateDefaultMarketPrice(product.getPrice());
			product.setMarketPrice(defaultMarketPrice);
		}
		if (product.getPoint() == null) {
			long point = calculateDefaultPoint(product.getPrice());
			product.setPoint(point);
		}
		
		product.setFullName(null);
		product.setAllocatedStock(0);
		product.setScore(0F);
		product.setTotalScore(0L);
		product.setScoreCount(0L);
		product.setHits(0L);
		product.setWeekHits(0L);
		product.setMonthHits(0L);
		product.setSales(0L);
		product.setWeekSales(0L);
		product.setMonthSales(0L);
		product.setWeekHitsDate(new Date());
		product.setMonthHitsDate(new Date());
		product.setWeekSalesDate(new Date());
		product.setMonthSalesDate(new Date());
		product.setReviews(null);
		product.setConsultations(null);
		product.setFavoriteMembers(null);
		//product.setPromotions(null);
		product.setCartItems(null);
		product.setOrderItems(null);
		product.setGiftItems(null);
		product.setProductNotifies(null);
		
		product.setCity(city);
		product.setRegion(shop.getRegion());
		product.setShop(shop);
		product.setTreePath(shopCategory.getTreePath());
		product.setShopCategory(shopCategory);
		
		for (MemberRank memberRank : memberRankService.findAll()) {
			String price = request.getParameter("memberPrice_" + memberRank.getId());
			if (StringUtils.isNotEmpty(price) && new BigDecimal(price).compareTo(new BigDecimal(0)) >= 0) {
				product.getMemberPrice().put(memberRank, new BigDecimal(price));
			} else {
				product.getMemberPrice().remove(memberRank);
			}
		}

		for (ProductImage productImage : product.getProductImages()) {
			imageService.build(productImage);
		}
		Collections.sort(product.getProductImages());
		if (product.getImage() == null && product.getThumbnail() != null) {
			product.setImage(product.getThumbnail());
		}
		
		for (ParameterGroup parameterGroup : product.getShopCategory().getParameterGroups()) {
			for (Parameter parameter : parameterGroup.getParameters()) {
				String parameterValue = request.getParameter("parameter_" + parameter.getId());
				if (StringUtils.isNotEmpty(parameterValue)) {
					product.getParameterValue().put(parameter, parameterValue);
				} else {
					product.getParameterValue().remove(parameter);
				}
			}
		}
		
		for (Attribute attribute : product.getShopCategory().getAttributes()) {
			String attributeValue = request.getParameter("attribute_" + attribute.getId());
			if (StringUtils.isNotEmpty(attributeValue)) {
				product.setAttributeValue(attribute, attributeValue);
			} else {
				product.setAttributeValue(attribute, null);
			}
		}
		
		List<ProductSpecification> productSpecifications = new ArrayList<ProductSpecification>();
		
		if (specificationIds != null && specificationIds.length > 0) {
			
			for (int i = 0; i < specificationIds.length; i++) {
				
				Specification specification = specificationService.find(specificationIds[i]);
				String[] specificationValueIds = request.getParameterValues("specification_" + specification.getId());
				
				if (specificationValueIds != null && specificationValueIds.length > 0) {
					
					for (int j = 0; j < specificationValueIds.length; j++) {
						
						if(i == 0) {
							
							//TODO:设置库存
							ProductSpecification productSpecification = new ProductSpecification();
							productSpecification.setProduct(product);
							productSpecifications.add(productSpecification);
							
						}
						
						SpecificationValue specificationValue = specificationValueService.find(Long.valueOf(specificationValueIds[j]));
						
						ProductSpecificationValue productSpecificationValue = new ProductSpecificationValue();
						productSpecificationValue.setSpecification(specification);
						productSpecificationValue.setSpecificationValue(specificationValue);
						productSpecificationValue.setProduct(product);
						productSpecificationValue.setProductSpecification(productSpecifications.get(j));
						
						productSpecifications.get(j).getProductSpecificationValues().add(productSpecificationValue);
						//product.getProductSpecificationValues().add(productSpecificationValue);
						
					}
				}
			}
			
			product.getProductSpecifications().addAll(productSpecifications);
			
		} else {
			product.setProductSpecifications(null);
			//product.setProductSpecificationValues(null);
		}
		
		productService.save(product);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		
		Product product = productService.find(id);
		Shop shop = product.getShop();
		
		model.addAttribute("shopCategories", shop.getShopCategories());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("tags", tagService.findList(Type.product));
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		model.addAttribute("product", product);
		
		return "/shopadmin/product/edit";
		
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Product product, Long shopCategoryId, Long brandId, Long[] tagIds, Long[] specificationIds, Long[] specificationProductIds, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = shopService.find(principal.getShopId());
		ShopCategory shopCategory = shopCategoryService.find(shopCategoryId);
		
		for (Iterator<ProductImage> iterator = product.getProductImages().iterator(); iterator.hasNext();) {
			ProductImage productImage = iterator.next();
			if (productImage == null || productImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (productImage.getFile() != null && !productImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, productImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:edit.jhtml?id=" + product.getId();
				}
			}
		}
		
		product.setBrand(brandService.find(brandId));
		product.setTags(new HashSet<Tag>(tagService.findList(tagIds)));
		product.setShopCategory(shopCategory);
		product.setCity(shop.getCity());
		product.setRegion(shop.getRegion());
		product.setShop(shop);
		
		Product pProduct = productService.find(product.getId());
		
		if (pProduct == null) {
			return ERROR_VIEW;
		}
		
		product.setIsTop(pProduct.getIsTop());
		
		if (!isValid(product)) {
			return ERROR_VIEW;
		}
		
		if (StringUtils.isNotEmpty(product.getSn()) && !productService.snUnique(pProduct.getSn(), product.getSn())) {
			return ERROR_VIEW;
		}
		if (product.getMarketPrice() == null) {
			BigDecimal defaultMarketPrice = calculateDefaultMarketPrice(product.getPrice());
			product.setMarketPrice(defaultMarketPrice);
		}
		if (product.getPoint() == null) {
			long point = calculateDefaultPoint(product.getPrice());
			product.setPoint(point);
		}

		for (MemberRank memberRank : memberRankService.findAll()) {
			String price = request.getParameter("memberPrice_" + memberRank.getId());
			if (StringUtils.isNotEmpty(price) && new BigDecimal(price).compareTo(new BigDecimal(0)) >= 0) {
				product.getMemberPrice().put(memberRank, new BigDecimal(price));
			} else {
				product.getMemberPrice().remove(memberRank);
			}
		}

		for (ProductImage productImage : product.getProductImages()) {
			imageService.build(productImage);
		}
		
		Collections.sort(product.getProductImages());
		if (product.getImage() == null && product.getThumbnail() != null) {
			product.setImage(product.getThumbnail());
		}
		
		for (ParameterGroup parameterGroup : product.getShopCategory().getParameterGroups()) {
			for (Parameter parameter : parameterGroup.getParameters()) {
				String parameterValue = request.getParameter("parameter_" + parameter.getId());
				if (StringUtils.isNotEmpty(parameterValue)) {
					product.getParameterValue().put(parameter, parameterValue);
				} else {
					product.getParameterValue().remove(parameter);
				}
			}
		}
		
		for (Attribute attribute : product.getShopCategory().getAttributes()) {
			String attributeValue = request.getParameter("attribute_" + attribute.getId());
			if (StringUtils.isNotEmpty(attributeValue)) {
				product.setAttributeValue(attribute, attributeValue);
			} else {
				product.setAttributeValue(attribute, null);
			}
		}
		
		List<ProductSpecification> productSpecifications = new ArrayList<ProductSpecification>();
		
		BeanUtils.copyProperties(product, pProduct, new String[] { "id", "createDate", "modifyDate", "fullName", "allocatedStock", "score", "totalScore", "scoreCount", "hits", "weekHits", "monthHits", "sales", "weekSales", "monthSales", "weekHitsDate", "monthHitsDate", "weekSalesDate", "monthSalesDate", "goods", "reviews", "consultations", "favoriteMembers",
				"specifications", "specificationValues", "promotions", "cartItems", "orderItems", "giftItems", "productNotifies"});
		
		if (specificationIds != null && specificationIds.length > 0) {
			
			for (int i = 0; i < specificationIds.length; i++) {
				
				Specification specification = specificationService.find(specificationIds[i]);
				String[] specificationValueIds = request.getParameterValues("specification_" + specification.getId());
				
				if (specificationValueIds != null && specificationValueIds.length > 0) {
					
					for (int j = 0; j < specificationValueIds.length; j++) {
						
						if(i == 0) {
							
							//TODO:设置库存
							ProductSpecification productSpecification = new ProductSpecification();
							productSpecification.setProduct(product);
							productSpecifications.add(productSpecification);
							
						}
						
						SpecificationValue specificationValue = specificationValueService.find(Long.valueOf(specificationValueIds[j]));
						
						ProductSpecificationValue productSpecificationValue = new ProductSpecificationValue();
						productSpecificationValue.setSpecification(specification);
						productSpecificationValue.setSpecificationValue(specificationValue);
						productSpecificationValue.setProduct(product);
						productSpecificationValue.setProductSpecification(productSpecifications.get(j));
						
						productSpecifications.get(j).getProductSpecificationValues().add(productSpecificationValue);
						//product.getProductSpecificationValues().add(productSpecificationValue);
						
					}
				}
			}
			
			product.getProductSpecifications().addAll(productSpecifications);
			
		} else {
			product.setProductSpecifications(null);
			//product.setProductSpecificationValues(null);
		}
		
		productService.update(product);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:list.jhtml";
		
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		productService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 计算默认市场价
	 * 
	 * @param price
	 *            价格
	 */
	private BigDecimal calculateDefaultMarketPrice(BigDecimal price) {
		Setting setting = SettingUtils.get();
		Double defaultMarketPriceScale = setting.getDefaultMarketPriceScale();
		return setting.setScale(price.multiply(new BigDecimal(defaultMarketPriceScale.toString())));
	}
	
	/**
	 * 计算默认积分
	 * 
	 * @param price
	 *            价格
	 */
	private long calculateDefaultPoint(BigDecimal price) {
		Setting setting = SettingUtils.get();
		Double defaultPointScale = setting.getDefaultPointScale();
		return price.multiply(new BigDecimal(defaultPointScale.toString())).longValue();
	}
	
}
