package cn.bmwm.modules.shop.controller.app;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Result;
import cn.bmwm.modules.shop.entity.Cart;
import cn.bmwm.modules.shop.entity.PaymentMethod;
import cn.bmwm.modules.shop.entity.Receiver;
import cn.bmwm.modules.shop.entity.ShippingMethod;
import cn.bmwm.modules.shop.service.CartService;
import cn.bmwm.modules.shop.service.OrderService;
import cn.bmwm.modules.shop.service.PaymentMethodService;
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
	 * 支付方式错误
	 */
	public static final int ORDER_PAYMENT_METHOD_ERROR = 104;
	
	/**
	 * 送货方式错误
	 */
	public static final int ORDER_SHIPING_METHOD_ERROR = 105;
	
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;
	
	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	

	/**
	 * 创建
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Result create(Long receiverId, Long paymentMethodId, Long shippingMethodId, String memo) {
		
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
		
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		if (paymentMethod == null) {
			return new Result(ORDER_PAYMENT_METHOD_ERROR, 1, "付款方式不存在！");
		}
		
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		if (shippingMethod == null) {
			return new Result(ORDER_SHIPING_METHOD_ERROR, 1, "送货方式不存在！");
		}
		
		if (!paymentMethod.getShippingMethods().contains(shippingMethod)) {
			return new Result(ORDER_SHIPING_METHOD_ERROR, 1, "付款方式不支持该送货方式！");
		}
		
		//Order order = orderService.create(cart, receiver, paymentMethod, shippingMethod, couponCode, isInvoice, invoiceTitle, useBalance, memo, null);
		
		return new Result(SUCCESS, 1);
		
	}
	
}
