/*


 * */
package cn.bmwm.modules.shop.controller.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Constants;
import cn.bmwm.modules.shop.controller.app.vo.CartProduct;
import cn.bmwm.modules.shop.controller.app.vo.CartShop;
import cn.bmwm.modules.shop.entity.Cart;
import cn.bmwm.modules.shop.entity.CartItem;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductSpecification;
import cn.bmwm.modules.shop.entity.Promotion;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.CartItemService;
import cn.bmwm.modules.shop.service.CartService;
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.shop.service.ProductService;

/**
 * Controller - 购物车
 * 
 *
 * @version 1.0
 */
@Controller("appShopCartController")
@RequestMapping(value = "/app/cart")
public class CartController extends AppBaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	
	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;

	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> add(HttpServletRequest request) {
		
		String sid = request.getParameter("id");
		String squantity = request.getParameter("quantity");
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("version", 1);
		
		if(StringUtils.isBlank(squantity)) {
			result.put("message", "商品数量错误！");
			result.put("flag", Constants.CART_QUANTITY_ERROR);
			return result;
		}
		
		int quantity = Integer.parseInt(squantity);
		
		if(quantity < 1) {
			result.put("message", "商品数量错误！");
			result.put("flag", Constants.CART_QUANTITY_ERROR);
			return result;
		}
		
		if(StringUtils.isBlank(sid)) {
			result.put("message", "商品不存在！");
			result.put("flag", Constants.CART_PRODUCT_NOT_EXISTS);
			return result;
		}
		
		long id = Long.parseLong(sid);
		Product product = productService.find(id);
		
		if (product == null) {
			result.put("message", "商品不存在！");
			result.put("flag", Constants.CART_PRODUCT_NOT_EXISTS);
			return result;
		}
		if (!product.getIsMarketable()) {
			result.put("message", "商品未上架！");
			result.put("flag", Constants.CART_PRODUCT_NOT_MARKETABLE);
			return result;
		}
		if (product.getIsGift()) {
			result.put("message", "该商品是赠品！");
			result.put("flag", Constants.CART_PRODUCT_GIFT);
			return result;
		}
		
		Cart cart = cartService.getAppCurrent();
		Member member = memberService.getAppCurrent();

		if (cart == null) {
			cart = new Cart();
			cart.setKey(UUID.randomUUID().toString() + DigestUtils.md5Hex(RandomStringUtils.randomAlphabetic(30)));
			cart.setMember(member);
			cartService.save(cart);
		}

		if (Cart.MAX_PRODUCT_COUNT != null && cart.getCartItems().size() >= Cart.MAX_PRODUCT_COUNT) {
			result.put("message", "商品数量超过购物车最大允许商品数量！");
			result.put("flag", Constants.CART_PRODUCT_MAX_COUNT);
			return result;
		}

		if (cart.contains(product)) {
			
			CartItem cartItem = cart.getCartItem(product);
			
			if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity() + quantity > CartItem.MAX_QUANTITY) {
				result.put("message", "商品数量超过单品最大允许数量！");
				result.put("flag", Constants.CART_ITEM_MAX_QUANTITY);
				return result;
			}
			
			if (product.getStock() != null && cartItem.getQuantity() + quantity > product.getAvailableStock()) {
				result.put("message", "商品数量超过商品库存！");
				result.put("flag", Constants.CART_PRODUCT_STOCK_QUANTITY);
				return result;
			}
			
			cartItem.add(quantity);
			cartItemService.update(cartItem);
			
		} else {
			
			if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
				result.put("message", "商品数量超过单品最大允许数量！");
				result.put("flag", Constants.CART_ITEM_MAX_QUANTITY);
				return result;
			}
			
			if (product.getStock() != null && quantity > product.getAvailableStock()) {
				result.put("message", "商品数量超过商品库存！");
				result.put("flag", Constants.CART_PRODUCT_STOCK_QUANTITY);
				return result;
			}
			
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItemService.save(cartItem);
			
		}
		
		/*
		if (member == null) {
			WebUtils.addCookie(request, response, Cart.ID_COOKIE_NAME, cart.getId().toString(), Cart.TIMEOUT);
			WebUtils.addCookie(request, response, Cart.KEY_COOKIE_NAME, cart.getKey(), Cart.TIMEOUT);
		}
		*/
		
		result.put("flag", 1);
		
		return result;
		
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> itemList() {
		return list();
	}
	
	/**
	 * 获取当前购物车中的商品列表
	 * @return
	 */
	private Map<String,Object> list() {
		
		Cart cart = cartService.getAppCurrent();
		List<CartShop> items = getCartShops(cart);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		result.put("flag", 1);
		result.put("data", items);
		
		return result;
		
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request) {
		
		String sid = request.getParameter("cartItemId");
		String squantity = request.getParameter("quantity");
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("version", 1);
		
		if(StringUtils.isBlank(squantity)) {
			result.put("message", "购物车商品数量错误！");
			result.put("flag", Constants.CART_QUANTITY_ERROR);
			return result;
		}
		
		int quantity = Integer.parseInt(squantity);
		
		if(quantity < 1) {
			result.put("message", "购物车商品数量错误！");
			result.put("flag", Constants.CART_QUANTITY_ERROR);
			return result;
		}
		
		if(StringUtils.isBlank(sid)) {
			result.put("message", "购物车商品不存在！");
			result.put("flag", Constants.CART_CART_ITEM_NOT_EXISTS);
			return result;
		}
		
		long id = Long.parseLong(sid);
		
		Cart cart = cartService.getAppCurrent();
		
		if (cart == null || cart.isEmpty()) {
			result.put("message", "购物车为空！");
			result.put("flag", Constants.CART_CART_EMPTY);
			return result;
		}
		
		CartItem cartItem = cartItemService.find(id);
		Set<CartItem> cartItems = cart.getCartItems();
		
		if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
			result.put("message", "购物车中不存在该商品！");
			result.put("flag", Constants.CART_CART_ITEM_NOT_EXISTS);
			return result;
		}
		
		if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
			result.put("message", "商品数量超过单品最大允许数量！");
			result.put("flag", Constants.CART_ITEM_MAX_QUANTITY);
			return result;
		}
		
		Product product = cartItem.getProduct();
		
		if (product.getStock() != null && quantity > product.getAvailableStock()) {
			result.put("message", "商品数量超过商品库存量！");
			result.put("flag", Constants.CART_PRODUCT_STOCK_QUANTITY);
			return result;
		}
		
		cartItem.setQuantity(quantity);
		cartItemService.update(cartItem);
		
		return list();
		
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request) {
		
		String sid = request.getParameter("cartItemId");
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("version", 1);
		
		if(StringUtils.isBlank(sid)) {
			result.put("message", "购物车中不存在该商品！");
			result.put("flag", Constants.CART_CART_ITEM_NOT_EXISTS);
			return result;
		}
		
		long id = Long.parseLong(sid);
		
		Cart cart = cartService.getAppCurrent();
		
		if (cart == null || cart.isEmpty()) {
			result.put("message", "购物车为空！");
			result.put("flag", Constants.CART_CART_EMPTY);
			return result;
		}
		
		CartItem cartItem = cartItemService.find(id);
		Set<CartItem> cartItems = cart.getCartItems();
		
		if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
			result.put("message", "购物车中不存在该商品！");
			result.put("flag", Constants.CART_CART_ITEM_NOT_EXISTS);
			return result;
		}
		
		cartItemService.delete(cartItem);
		
		return list();
		
	}

	/**
	 * 清空
	 */
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> clear() {
		
		Cart cart = cartService.getAppCurrent();
		cartService.delete(cart);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		result.put("flag", 1);
		
		return result;
		
	}
	
	/**
	 * 转换数据结构
	 * @param cart
	 * @return
	 */
	public List<CartShop> getCartShops(Cart cart) {
		
		List<CartShop> list = new ArrayList<CartShop>();
		
		if(cart == null) return list;
		
		Set<CartItem> items = cart.getCartItems();
		
		if(items == null || items.size() == 0) return list;
		
		Map<Long,CartShop> shopMap = new HashMap<Long,CartShop>();
		
		for(CartItem item : items) {
			
			Product product = item.getProduct();
			Shop shop = product.getShop();
			
			CartProduct cproduct = new CartProduct();
			cproduct.setId(product.getId());
			cproduct.setName(product.getName());
			cproduct.setPrice(product.getPrice());
			cproduct.setDiscountPrice(caculatePrice(item));
			cproduct.setQuantity(item.getQuantity());
			cproduct.setCartItemId(item.getId());
			cproduct.setImageUrl(product.getImage());
			
			//商品规格
			List<ProductSpecification> productSpecifications = product.getProductSpecificationList();
			
			if(productSpecifications != null && productSpecifications.size() > 0) {
				List<String> specificationList = new ArrayList<String>();
				for(ProductSpecification specification : productSpecifications) {
					specificationList.add(specification.getSpecification().getName() + " : " + specification.getSpecificationValue().getName());
				}
				cproduct.setSpecificationList(specificationList);
			}
			
			if(shopMap.get(shop.getId()) == null) {
				
				CartShop cshop = new CartShop();
				cshop.setShopName(shop.getName());
				
				cshop.setShopActivity(getShopActivity(item));
				
				List<CartProduct> productList = new ArrayList<CartProduct>();
				productList.add(cproduct);
				cshop.setProductList(productList);
				
				shopMap.put(shop.getId(), cshop);
				
			}else {
				CartShop cshop = shopMap.get(shop.getId());
				cshop.getProductList().add(cproduct);
			}
			
		}
		
		list.addAll(shopMap.values());
		
		//按商铺计算总价
		for(CartShop shop : list) {
			
			List<CartProduct> productList = shop.getProductList();
			BigDecimal totalPrice = new BigDecimal(0);
			
			for(CartProduct product : productList) {
				totalPrice = totalPrice.add(product.getDiscountPrice());
			}
			
			shop.setTotalPrice(totalPrice);
			
		}
		
		return list;
		
	}
	
	/**
	 * 计算价格
	 * @param product
	 * @return
	 */
	public BigDecimal caculatePrice(CartItem item) {
		
		Product product = item.getProduct();
		BigDecimal currentPrice = product.getPrice();
		
		Set<Promotion> productPromotions = product.getValidPromotions();
		
		if(productPromotions != null && productPromotions.size() > 0) {
			for(Promotion promotion : productPromotions) {
				currentPrice = promotion.calculatePrice(item.getQuantity(), currentPrice);
			}
		}
		
		return currentPrice;
		
	}
	
	/**
	 * 获取店铺活动
	 * @param item
	 * @return
	 */
	public String getShopActivity(CartItem item) {
		
		Set<Promotion> promotions = item.getProduct().getShop().getPromotions();
		
		if(promotions == null || promotions.size() == 0) return "";
		
		for(Promotion promotion : promotions) {
			if (promotion != null && promotion.hasBegun() && !promotion.hasEnded()) {
				return promotion.getTitle();
			}
		}
		
		return "";
		
	}

}
