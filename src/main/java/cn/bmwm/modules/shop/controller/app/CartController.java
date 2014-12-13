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

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Constants;
import cn.bmwm.common.Result;
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
	public Result add(Long id, Integer quantity) {
		
		if(id == null) {
			return new Result(Constants.CART_PRODUCT_NOT_EXISTS, 1, "商品不存在！");
		}
		
		if(quantity == null) {
			return new Result(Constants.CART_QUANTITY_ERROR, 1, "商品数量错误！");
		}
		
		if(quantity < 1) {
			return new Result(Constants.CART_QUANTITY_ERROR, 1, "商品数量错误！");
		}
		
		Product product = productService.find(id);
		
		if (product == null) {
			return new Result(Constants.CART_PRODUCT_NOT_EXISTS, 1, "商品不存在！");
		}
		
		if (!product.getIsMarketable()) {
			return new Result(Constants.CART_PRODUCT_NOT_MARKETABLE, 1, "商品未上架！");
		}
		
		if (product.getIsGift()) {
			return new Result(Constants.CART_PRODUCT_GIFT, 1, "该商品是赠品！");
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
			return new Result(Constants.CART_PRODUCT_MAX_COUNT, 1, "商品数量超过购物车最大允许商品数量！");
		}

		if (cart.contains(product)) {
			
			CartItem cartItem = cart.getCartItem(product);
			
			if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity() + quantity > CartItem.MAX_QUANTITY) {
				return new Result(Constants.CART_ITEM_MAX_QUANTITY, 1, "商品数量超过单品最大允许数量！");
			}
			
			if (product.getStock() != null && cartItem.getQuantity() + quantity > product.getAvailableStock()) {
				return new Result(Constants.CART_PRODUCT_STOCK_QUANTITY, 1, "商品数量超过商品库存！");
			}
			
			cartItem.add(quantity);
			cartItemService.update(cartItem);
			
		} else {
			
			if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
				return new Result(Constants.CART_ITEM_MAX_QUANTITY, 1, "商品数量超过单品最大允许数量！");
			}
			
			if (product.getStock() != null && quantity > product.getAvailableStock()) {
				return new Result(Constants.CART_PRODUCT_STOCK_QUANTITY, 1, "商品数量超过商品库存！");
			}
			
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItemService.save(cartItem);
			
		}
		
		return new Result(Constants.SUCCESS, 1);
		
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Result itemList() {
		return list();
	}
	
	/**
	 * 获取当前购物车中的商品列表
	 * @return
	 */
	private Result list() {
		
		Cart cart = cartService.getAppCurrent();
		List<CartShop> items = getCartShops(cart);
		
		return new Result(Constants.SUCCESS, 1, "", items);
		
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(Long cartItemId, Integer quantity) {
		
		if(cartItemId == null) {
			return new Result(Constants.CART_CART_ITEM_NOT_EXISTS, 1, "购物车商品不存在！");
		}
		
		if(quantity == null) {
			return new Result(Constants.CART_QUANTITY_ERROR, 1, "购物车商品数量错误！");
		}
		
		if(quantity < 1) {
			return new Result(Constants.CART_QUANTITY_ERROR, 1, "购物车商品数量错误！");
		}
		
		Cart cart = cartService.getAppCurrent();
		
		if (cart == null || cart.isEmpty()) {
			return new Result(Constants.CART_CART_EMPTY, 1, "购物车为空！");
		}
		
		CartItem cartItem = cartItemService.find(cartItemId);
		Set<CartItem> cartItems = cart.getCartItems();
		
		if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
			return new Result(Constants.CART_CART_ITEM_NOT_EXISTS, 1, "购物车中不存在该商品！");
		}
		
		if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
			return new Result(Constants.CART_ITEM_MAX_QUANTITY, 1, "商品数量超过单品最大允许数量！");
		}
		
		Product product = cartItem.getProduct();
		
		if (product.getStock() != null && quantity > product.getAvailableStock()) {
			return new Result(Constants.CART_PRODUCT_STOCK_QUANTITY, 1, "商品数量超过商品库存量！");
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
	public Result delete(Long cartItemId) {
		
		if(cartItemId == null) {
			return new Result(Constants.CART_CART_ITEM_NOT_EXISTS, 1, "购物车中不存在该商品！");
		}
		
		Cart cart = cartService.getAppCurrent();
		
		if (cart == null || cart.isEmpty()) {
			return new Result(Constants.CART_CART_EMPTY, 1, "购物车为空！");
		}
		
		CartItem cartItem = cartItemService.find(cartItemId);
		Set<CartItem> cartItems = cart.getCartItems();
		
		if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
			return new Result(Constants.CART_CART_ITEM_NOT_EXISTS, 1, "购物车中不存在该商品！");
		}
		
		cartItemService.delete(cartItem);
		
		return list();
		
	}

	/**
	 * 清空
	 */
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	@ResponseBody
	public Result clear() {
		
		Cart cart = cartService.getAppCurrent();
		cartService.delete(cart);
		
		return new Result(Constants.SUCCESS, 1);
		
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
