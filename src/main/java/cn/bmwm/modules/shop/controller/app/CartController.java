/*


 * */
package cn.bmwm.modules.shop.controller.app;

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

import cn.bmwm.common.utils.Constants;
import cn.bmwm.modules.shop.controller.app.vo.CartProduct;
import cn.bmwm.modules.shop.controller.app.vo.CartShop;
import cn.bmwm.modules.shop.entity.Cart;
import cn.bmwm.modules.shop.entity.CartItem;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Product;
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
public class CartController extends AppBaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	
	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;
	
	@RequestMapping(value = "/app/cart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> execute(String apiName, HttpServletRequest request) {
		
		if("add".equals(apiName)) {
			return add(request);
		}else if("list".equals(apiName)) {
			return list();
		}else if("update".equals(apiName)) {
			return update(request);
		}else if("delete".equals(apiName)) {
			return delete(request);
		}else if("clear".equals(apiName)) {
			return clear();
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("verson", 1);
		result.put("flag", 404);
		
		return result;
		
	}

	/**
	 * 添加
	 */
	public Map<String,Object> add(HttpServletRequest request) {
		
		String sid = request.getParameter("id");
		String squantity = request.getParameter("quantity");
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("version", 1);
		
		if(StringUtils.isBlank(squantity)) {
			result.put("flag", Constants.CART_QUANTITY_ERROR);
			return result;
		}
		
		int quantity = Integer.parseInt(squantity);
		
		if(quantity < 1) {
			result.put("flag", Constants.CART_QUANTITY_ERROR);
			return result;
		}
		
		if(StringUtils.isBlank(sid)) {
			result.put("flag", Constants.CART_PRODUCT_NOT_EXISTS);
			return result;
		}
		
		long id = Long.parseLong(sid);
		
		Product product = productService.find(id);
		
		if (product == null) {
			result.put("flag", Constants.CART_PRODUCT_NOT_EXISTS);
			return result;
		}
		if (!product.getIsMarketable()) {
			result.put("flag", Constants.CART_PRODUCT_NOT_MARKETABLE);
			return result;
		}
		if (product.getIsGift()) {
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
			result.put("flag", Constants.CART_PRODUCT_MAX_COUNT);
			return result;
		}

		if (cart.contains(product)) {
			
			CartItem cartItem = cart.getCartItem(product);
			
			if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity() + quantity > CartItem.MAX_QUANTITY) {
				result.put("flag", Constants.CART_ITEM_MAX_QUANTITY);
				return result;
			}
			
			if (product.getStock() != null && cartItem.getQuantity() + quantity > product.getAvailableStock()) {
				result.put("flag", Constants.CART_PRODUCT_STOCK_QUANTITY);
				return result;
			}
			
			cartItem.add(quantity);
			cartItemService.update(cartItem);
			
		} else {
			
			if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
				result.put("flag", Constants.CART_ITEM_MAX_QUANTITY);
				return result;
			}
			
			if (product.getStock() != null && quantity > product.getAvailableStock()) {
				result.put("flag", Constants.CART_PRODUCT_STOCK_QUANTITY);
				return result;
			}
			
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItemService.save(cartItem);
			
			cart.getCartItems().add(cartItem);
			
		}
		
		/*
		if (member == null) {
			WebUtils.addCookie(request, response, Cart.ID_COOKIE_NAME, cart.getId().toString(), Cart.TIMEOUT);
			WebUtils.addCookie(request, response, Cart.KEY_COOKIE_NAME, cart.getKey(), Cart.TIMEOUT);
		}
		*/
		
		result.put("flag", 1);
		
		List<CartShop> items = getCartShops(cart);
		result.put("data", items);
		
		return result;
		
	}

	/**
	 * 列表
	 */
	public Map<String,Object> list() {
		
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
	public Map<String, Object> update(HttpServletRequest request) {
		
		String sid = request.getParameter("id");
		String squantity = request.getParameter("quantity");
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("version", 1);
		
		if(StringUtils.isBlank(squantity)) {
			result.put("flag", Constants.CART_QUANTITY_ERROR);
			return result;
		}
		
		int quantity = Integer.parseInt(squantity);
		
		if(quantity < 1) {
			result.put("flag", Constants.CART_QUANTITY_ERROR);
			return result;
		}
		
		if(StringUtils.isBlank(sid)) {
			result.put("flag", Constants.CART_PRODUCT_NOT_EXISTS);
			return result;
		}
		
		long id = Long.parseLong(sid);
		
		Cart cart = cartService.getAppCurrent();
		
		if (cart == null || cart.isEmpty()) {
			result.put("flag", Constants.CART_CART_EMPTY);
			return result;
		}
		
		CartItem cartItem = cartItemService.find(id);
		Set<CartItem> cartItems = cart.getCartItems();
		
		if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
			result.put("flag", Constants.CART_CART_ITEM_NOT_EXISTS);
			return result;
		}
		
		if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
			result.put("flag", Constants.CART_ITEM_MAX_QUANTITY);
			return result;
		}
		
		Product product = cartItem.getProduct();
		
		if (product.getStock() != null && quantity > product.getAvailableStock()) {
			result.put("flag", Constants.CART_PRODUCT_STOCK_QUANTITY);
			return result;
		}
		
		cartItem.setQuantity(quantity);
		cartItemService.update(cartItem);

		result.put("flag", 1);
		
		return result;
		
	}

	/**
	 * 删除
	 */
	public Map<String, Object> delete(HttpServletRequest request) {
		
		String sid = request.getParameter("id");
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("version", 1);
		
		if(StringUtils.isBlank(sid)) {
			result.put("flag", Constants.CART_PRODUCT_NOT_EXISTS);
			return result;
		}
		
		long id = Long.parseLong(sid);
		
		Cart cart = cartService.getAppCurrent();
		
		if (cart == null || cart.isEmpty()) {
			result.put("flag", Constants.CART_CART_EMPTY);
			return result;
		}
		
		CartItem cartItem = cartItemService.find(id);
		Set<CartItem> cartItems = cart.getCartItems();
		
		if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
			result.put("flag", Constants.CART_CART_ITEM_NOT_EXISTS);
			return result;
		}
		
		cartItems.remove(cartItem);
		cartItemService.delete(cartItem);

		result.put("flag", 1);
		
		return result;
		
	}

	/**
	 * 清空
	 */
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
			cproduct.setPrice(product.getPrice().doubleValue());
			cproduct.setQuantity(item.getQuantity());
			cproduct.setCartItemId(item.getId());
			
			//TODO:商品规格
			cproduct.setSpecification("");
			
			if(shopMap.get(shop.getId()) == null) {
				
				CartShop cshop = new CartShop();
				cshop.setShopName(shop.getName());
				
				//TODO:店铺促销活动
				cshop.setShopActivity("");
				
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
		
		return list;
		
	}

}