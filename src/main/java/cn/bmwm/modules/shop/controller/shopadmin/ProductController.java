/**
 * 
 */
package cn.bmwm.modules.shop.controller.shopadmin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.Brand;
import cn.bmwm.modules.shop.entity.Product.OrderType;
import cn.bmwm.modules.shop.entity.Promotion;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.ShopCategory;
import cn.bmwm.modules.shop.entity.Tag;
import cn.bmwm.modules.shop.entity.Tag.Type;
import cn.bmwm.modules.shop.service.BrandService;
import cn.bmwm.modules.shop.service.FileService;
import cn.bmwm.modules.shop.service.GoodsService;
import cn.bmwm.modules.shop.service.MemberRankService;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ProductImageService;
import cn.bmwm.modules.shop.service.ProductService;
import cn.bmwm.modules.shop.service.PromotionService;
import cn.bmwm.modules.shop.service.ShopCategoryService;
import cn.bmwm.modules.shop.service.SpecificationService;
import cn.bmwm.modules.shop.service.SpecificationValueService;
import cn.bmwm.modules.shop.service.TagService;
import cn.bmwm.modules.sys.security.Principal;

/**
 * 店铺商品管理
 * @author zhoupuyue
 * @date 2014-8-20
 */
@Controller("shopProductController")
@RequestMapping("/shopadmin/product")
public class ProductController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	
	@Resource(name = "productImageServiceImpl")
	private ProductImageService productImageService;
	
	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	
	@Resource(name = "specificationValueServiceImpl")
	private SpecificationValueService specificationValueService;
	
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	
	@Resource(name = "shopCategoryServiceImpl")
	private ShopCategoryService shopCategoryService;
	
	
	/**
	 * 店铺商品列表
	 */
	//zhoupuyue,增加店铺商品列表
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String shoplist(Long shopCategoryId, Long brandId, Long promotionId, Long tagId, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert, Pageable pageable, ModelMap model) {
		
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Shop shop = principal.getShop();
		
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
		return "/admin/product/list";
	}
	
}
