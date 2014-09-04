/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.persistence.Order;
import cn.bmwm.modules.shop.controller.app.vo.Evaluate;
import cn.bmwm.modules.shop.controller.app.vo.ItemPage;
import cn.bmwm.modules.shop.controller.app.vo.ProductDetail;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductCategory;
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
	 * 商品列表
	 * 首页和一级分类下的商品推荐,点击更多,显示该分类下的商品列表
	 * catId : 分类ID
	 * city : 城市
	 * page : 页码
	 * size : 每页显示商品数
	 * order : 排序方式，1：促销，2：新品，3：销量，4：推荐
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> list(Long catId, String city, Integer page, Integer size, Integer order) {
		
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
		
		if(page == null) page = 1;
		if(size == null) size = 10;
		if(order == null) order = 1;
		
		ItemPage<Product> itemPage = productService.findProductList(city, category, page, size, order);
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", getProductItems(itemPage.getList()));
		
		return result;
		
	}
	
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
		detail.setPriceType("销售价");
		detail.setImageurl(product.getImage());
		detail.setEvaluateNumber(reviewService.count(null, product, null, true));
		detail.setScore(product.getAvgScore());
		detail.setDescurl(product.getIntroduction());
		//detail.setDesc(product.getattribute);
		detail.setStoreId(shop.getId());
		detail.setStoreName(shop.getName());
		detail.setStoreImageUrl(shop.getImage());
		
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
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("flag", 1);
		result.put("version", 1);
		result.put("data", detail);
		
		return result;
		
	}
	
}
