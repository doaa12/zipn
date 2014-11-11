/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.persistence.Order;
import cn.bmwm.modules.shop.controller.app.vo.Evaluate;
import cn.bmwm.modules.shop.controller.app.vo.ProductDetail;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.entity.ProductImage;
import cn.bmwm.modules.shop.entity.ProductSpecification;
import cn.bmwm.modules.shop.entity.ProductSpecificationValue;
import cn.bmwm.modules.shop.entity.Review;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.ProductCategoryService;
import cn.bmwm.modules.shop.service.ProductService;
import cn.bmwm.modules.shop.service.ReviewService;
import cn.bmwm.modules.sys.exception.BusinessException;

/**
 * App - 商品
 * @author zhoupuyue
 * @date 2014-8-27
 */
@Controller("appProductController")
@RequestMapping(value = "/app/product")
public class ProductController extends AppBaseController {
	
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "reviewServiceImpl")
	private ReviewService reviewService;

	
	/**
	 * 商品详情页
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> index(Long id) {
		
		if(id == null) {
			throw new BusinessException(" Parameter 'id' can not be null ! ");
		}
		
		Product product = productService.find(id);
		
		if(product == null) {
			throw new BusinessException(" Invalid Parameter 'id' ");
		}
		
		Shop shop = product.getShop();
		
		ProductDetail detail = new ProductDetail();
		
		detail.setCode(product.getId());
		detail.setTitle(product.getName());
		detail.setPrice(product.getPrice().doubleValue());
		detail.setOriginalPrice(product.getMarketPrice().doubleValue());
		detail.setPriceType(product.getPriceType());
		detail.setDesc(product.getDescription());
		
		detail.setEvaluateNumber(reviewService.count(null, product, null, true));
		detail.setScore(product.getAvgScore());
		detail.setIntroduction(product.getIntroduction());
		detail.setStoreId(shop.getId());
		detail.setStoreName(shop.getName());
		detail.setPhone(shop.getTelephone());
		detail.setStoreImageUrl(shop.getImage());
		
		List<ProductImage> images = product.getProductImages();
		List<String> imageList = new ArrayList<String>();
		
		if(images != null && images.size() > 0) {
			for(ProductImage image : images) {
				imageList.add(image.getSource());
			}
		}
		
		detail.setImageList(imageList);
		
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order("createDate", Order.Direction.desc));
		
		List<Review> reviewList = reviewService.findList(null, product, null, true, 2, null, orders);
		
		List<Evaluate> evaluates = new ArrayList<Evaluate>();
		
		if(reviewList != null && reviewList.size() > 0) {
			for(Review review : reviewList) {
				Evaluate evaluate = new Evaluate();
				evaluate.setId(review.getId());
				evaluate.setName(review.getUser());
				evaluate.setScore(review.getScore());
				evaluate.setDesc(review.getContent());
				evaluates.add(evaluate);
			}
		}
		
		detail.setEvaluate(evaluates);
		
		//商品规格
		List<Map<String,Object>> specifications = new ArrayList<Map<String,Object>>();
		
		Set<ProductSpecification> productSpecifications = product.getProductSpecifications();
		
		if(productSpecifications != null && productSpecifications.size() > 0) {
			
			for(ProductSpecification productSpecification : productSpecifications) {
				
				List<ProductSpecificationValue> productSpecificationValues = productSpecification.getProductSpecificationValues();
				Map<String,Object> specification = new HashMap<String,Object>();
				
				for(int i = 0 ; i < productSpecificationValues.size() ; i++ ) {
					specification.put("spec" + (i + 1), productSpecificationValues.get(i).getSpecificationValue().getName());
				}
				
				specification.put("specId", productSpecification.getId());
				specifications.add(specification);
				
			}
		}
		
		detail.setSpecifications(specifications);
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", detail);
		
		return result;
		
	}
	
	/**
	 * 商品列表
	 * 首页和一级分类下的商品推荐,点击更多,显示该分类下的推荐商品列表
	 * catId : 分类ID
	 * city : 城市
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> list(Long catId, String city) {
		
		if(catId == null) {
			throw new BusinessException(" Parameter 'catId' can not be null ! ");
		}
		
		if(city == null || city.trim().equals("")) {
			throw new BusinessException(" Parameter 'city' can not be empty ! ");
		}
		
		ProductCategory category = productCategoryService.find(catId);
		
		if(category == null) {
			throw new BusinessException(" Invalid Parameter 'catId' ! ");
		}
		
		List<Product> productList = productService.findRecommendList(city, category);
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", getProductItems(productList));
		
		return result;
		
	}
	
}
