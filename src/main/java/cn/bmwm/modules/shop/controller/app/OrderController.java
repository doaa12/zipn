package cn.bmwm.modules.shop.controller.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Constants;
import cn.bmwm.common.Result;
import cn.bmwm.modules.shop.controller.app.vo.CartItemVo;
import cn.bmwm.modules.shop.controller.app.vo.PrepareOrderVo;
import cn.bmwm.modules.shop.entity.Cart;
import cn.bmwm.modules.shop.entity.CartItem;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Order;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.Receiver;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.CartService;
import cn.bmwm.modules.shop.service.OrderService;
import cn.bmwm.modules.shop.service.ReceiverService;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * App -- 订单
 * @author zby
 * 2014-11-20 下午4:02:48
 */
@Controller
@RequestMapping(value = "/app/order")
public class OrderController extends AppBaseController {
	
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
	 * 订单不属于该用户
	 */
	public static final int ORDER_CART_OWNER_ERROR = 104;
	
	/**
	 * 订单中包含多个店铺的商品
	 */
	public static final int ORDER_MULTIPLE_SHOP = 105;
	
	
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;
	
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	
	
	/**
	 * 准备订单
	 */
	@RequestMapping(value = "/prepare", method = RequestMethod.POST)
	@ResponseBody
	public Result prepare(HttpSession session) {
		
		List<CartItemVo> cartItemList = new ArrayList<CartItemVo>();
		
		Member member = (Member)session.getAttribute(Constants.USER_LOGIN_MARK);
		
		Shop shop = null;
		
		Cart cart = cartService.getAppCurrent();
		
		if (cart == null || cart.isEmpty()) {
			return new Result(ORDER_CART_EMPTY, 1, "购物车为空！");
		}
		
		Set<CartItem> selectedCartItems = cart.getSelectedCartItems();
		
		if(selectedCartItems == null || selectedCartItems.size() == 0) {
			return new Result(ORDER_CART_EMPTY, 1, "请选择需要下单的商品！");
		}
		
		for(CartItem item : selectedCartItems) {
			
			if(!member.equals(item.getCart().getMember())) {
				log.warn("购物车项不属于该用户！cartItemId = " + item + ",memberId = " + member.getId());
				return new Result(ORDER_CART_OWNER_ERROR, 1, "订单项不属于该用户！");
			}
			
			if(item.getIsLowStock()) {
				log.warn("商品库存不足！");
				return new Result(ORDER_LOW_STOCK, 1, "商品库存不足！");
			}
			
			Product product = item.getProduct();
			Shop pshop = product.getShop();
			
			if(shop == null) {
				shop = pshop;
			}else {
				if(!shop.equals(pshop)) {
					return new Result(ORDER_MULTIPLE_SHOP, 1, "购物车中包含多个店铺的商品！");
				}
			}
			
			CartItemVo cartItem = new CartItemVo();
			cartItem.setId(product.getId());
			cartItem.setName(product.getName());
			cartItem.setPrice(product.getPrice());
			cartItem.setDiscountPrice(item.getSubtotal());
			cartItem.setQuantity(item.getQuantity());
			cartItem.setCartItemId(item.getId());
			cartItem.setIsSelected(item.getIsSelected());
			cartItem.setImageUrl(product.getImage());
			
			cartItemList.add(cartItem);
			
		}
		
		Receiver receiver = receiverService.findDefault(member);
		
		Order order = orderService.build(cart, receiver, "");
		
		Setting setting = SettingUtils.get();
		
		PrepareOrderVo prepareOrderVo = new PrepareOrderVo();
		prepareOrderVo.setShopName(shop.getName());
		prepareOrderVo.setReceiverAddress(receiver == null ? "" : receiver.getAddress());
		prepareOrderVo.setReceiverPhone(receiver == null ? "" : receiver.getPhone());
		prepareOrderVo.setReceiverUserName(receiver == null ? "" : receiver.getConsignee());
		prepareOrderVo.setTotalPrice(order.getTotalAmount());
		prepareOrderVo.setFreight(order.getFreight());
		prepareOrderVo.setPoints((int)(setting.getPointPercent() * cart.getPrice().doubleValue()));
		prepareOrderVo.setCartItemList(cartItemList);
		
		return new Result(Constants.SUCCESS, 1, "", prepareOrderVo);
		
	}

	/**
	 * 创建订单
	 * @param receiverId
	 * @param shippingMethodId
	 * @param memo
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Result create(Long receiverId, String memo) {
		
		Cart cart = cartService.getAppCurrent();
		
		if (cart == null || cart.isEmpty()) {
			return new Result(ORDER_CART_EMPTY, 1, "购物车为空！");
		}
		
		Set<CartItem> selectedCartItems = cart.getSelectedCartItems();
		
		if(selectedCartItems == null || selectedCartItems.size() == 0) {
			return new Result(ORDER_CART_EMPTY, 1, "请选择需要下单的商品！");
		}
		
		if (cart.getIsLowStock()) {
			return new Result(ORDER_LOW_STOCK, 1, "库存不足！");
		}
		
		Receiver receiver = receiverService.find(receiverId);
		if (receiver == null) {
			return new Result(ORDER_RECEIVE_ADDRESS_ERROR, 1, "收货地址不存在！");
		}
		
		orderService.create(cart, receiver, memo);
		
		return new Result(SUCCESS, 1);
		
	}
	
}
