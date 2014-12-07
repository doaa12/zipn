package cn.bmwm.modules.shop.controller.app;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.utils.Message;
import cn.bmwm.modules.shop.entity.Cart;
import cn.bmwm.modules.shop.entity.Order;
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
	public Map<String,Object> create(Long receiverId, Long paymentMethodId, Long shippingMethodId, String memo) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		Cart cart = cartService.getCurrent();
		
		if (cart == null || cart.isEmpty()) {
			return Message.warn("shop.order.cartNotEmpty");
		}
		if (!StringUtils.equals(cart.getToken(), cartToken)) {
			return Message.warn("shop.order.cartHasChanged");
		}
		if (cart.getIsLowStock()) {
			return Message.warn("shop.order.cartLowStock");
		}
		Receiver receiver = receiverService.find(receiverId);
		if (receiver == null) {
			return Message.error("shop.order.receiverNotExsit");
		}
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		if (paymentMethod == null) {
			return Message.error("shop.order.paymentMethodNotExsit");
		}
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		if (shippingMethod == null) {
			return Message.error("shop.order.shippingMethodNotExsit");
		}
		if (!paymentMethod.getShippingMethods().contains(shippingMethod)) {
			return Message.error("shop.order.deliveryUnsupported");
		}
		Order order = orderService.create(cart, receiver, paymentMethod, shippingMethod, couponCode, isInvoice, invoiceTitle, useBalance, memo, null);
		return Message.success(order.getSn());
	}
	
}
