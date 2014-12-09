package cn.bmwm.modules.shop.controller.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Constants;
import cn.bmwm.common.Result;
import cn.bmwm.modules.shop.controller.app.vo.CartProduct;
import cn.bmwm.modules.shop.entity.Cart;
import cn.bmwm.modules.shop.entity.CartItem;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Receiver;
import cn.bmwm.modules.shop.entity.ShippingMethod;
import cn.bmwm.modules.shop.service.CartItemService;
import cn.bmwm.modules.shop.service.CartService;
import cn.bmwm.modules.shop.service.OrderService;
import cn.bmwm.modules.shop.service.ReceiverService;
import cn.bmwm.modules.shop.service.ShippingMethodService;

/**
 * App -- 订单
 * @author zby
 * 2014-11-20 下午4:02:48
 */
@Controller
@RequestMapping(value = "/app/order")
public class OrderController {
	
	public static final Log log = LogFactory.getLog(OrderController.class);
	
	/**
	 * 处理完成
	 */
	public static final int SUCCESS = 1;
	
	/**
	 * 购物车为空
	 */
	public static final int ORDER_CART_EMPTY = 101;
	
	/**
	 * 库存不足
	 */
	public static final int ORDER_LOW_STOCK = 102;
	
	/**
	 * 收货地址错误
	 */
	public static final int ORDER_RECEIVE_ADDRESS_ERROR = 103;
	
	/**
	 * 送货方式错误
	 */
	public static final int ORDER_SHIPING_METHOD_ERROR = 104;
	
	/**
	 * 提交订单参数为空
	 */
	public static final int ORDER_PARAMETER_EMPTY = 105;
	
	/**
	 * 提交订单参数格式错误
	 */
	public static final int ORDER_PARAMETER_ERROR = 106;
	
	/**
	 * 购物车项ID错误
	 */
	public static final int ORDER_CART_ITEM_ID_ERROR = 107;
	
	/**
	 * 购物车商品数量错误
	 */
	public static final int ORDER_CART_QUANTITY_ERROR = 108;
	
	/**
	 * 订单项不属于该用户
	 */
	public static final int ORDER_CART_OWNER_ERROR = 109;
	
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	
	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;
	
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;
	
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	
	
	/**
	 * 准备订单
	 */
	@RequestMapping(value = "/prepare", method = RequestMethod.POST)
	@ResponseBody
	public Result prepare(String items, HttpSession session) {
		
		if(StringUtils.isBlank(items)) {
			return new Result(ORDER_PARAMETER_EMPTY, 1, "请选择需要下单的商品！");
		}
		
		String[] array = items.split(",");
		
		if(array == null || array.length == 0) {
			return new Result(ORDER_PARAMETER_EMPTY, 1, "请选择需要下单的商品！");
		}
		
		List<CartProduct> cartProductList = new ArrayList<CartProduct>();
		
		for(int i = 0 ; i < array.length ; i++ ) {
			
			if(StringUtils.isBlank(array[i])) {
				log.warn("提交订单参数格式错误！items = " + items);
				return new Result(ORDER_PARAMETER_ERROR, 1, "提交订单参数格式错误！");
			}
			
			long cartItemId = Long.parseLong(array[i]);
			
			if(cartItemId <= 0) {
				log.warn("购物车项ID小于0！");
				return new Result(ORDER_CART_ITEM_ID_ERROR, 1, "购物车项ID错误！");
			}
			
			CartItem cartItem = cartItemService.find(cartItemId);
			
			if(cartItem == null) {
				log.warn("清单项ID错误，购物车中不存在该订单项！cartItemId = " + cartItemId);
				return new Result(ORDER_CART_ITEM_ID_ERROR, 1, "购物车项ID错误！");
			}
			
			Member member = (Member)session.getAttribute(Constants.USER_LOGIN_MARK);
			
			if(!member.equals(cartItem.getCart().getMember())) {
				log.warn("订单项不属于该用户！cartItemId = " + cartItemId + ",memberId = " + member.getId());
				return new Result(ORDER_CART_OWNER_ERROR, 1, "订单项不属于该用户！");
			}
			
			if(cartItem.getIsLowStock()) {
				log.warn("商品库存不足！");
				return new Result(ORDER_CART_QUANTITY_ERROR, 1, "商品库存不足！");
			}
			
			CartProduct cp = new CartProduct();
			
		}
		
		return new Result(Constants.SUCCESS);
		
	}

	/**
	 * 创建
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Result create(Long receiverId, Long shippingMethodId, String memo) {
		
		Cart cart = cartService.getCurrent();
		
		if (cart == null || cart.isEmpty()) {
			return new Result(ORDER_CART_EMPTY, 1, "购物车为空！");
		}
		
		if (cart.getIsLowStock()) {
			return new Result(ORDER_LOW_STOCK, 1, "库存不足！");
		}
		
		Receiver receiver = receiverService.find(receiverId);
		if (receiver == null) {
			return new Result(ORDER_RECEIVE_ADDRESS_ERROR, 1, "收货地址不存在！");
		}
		
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		if (shippingMethod == null) {
			return new Result(ORDER_SHIPING_METHOD_ERROR, 1, "送货方式不存在！");
		}
		
		//Order order = orderService.create(cart, receiver, paymentMethod, shippingMethod, couponCode, isInvoice, invoiceTitle, useBalance, memo, null);
		
		return new Result(SUCCESS, 1);
		
	}
	
}
