package cn.bmwm.modules.shop.controller.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Result;
import cn.bmwm.modules.shop.controller.app.vo.CartItemVo;
import cn.bmwm.modules.shop.controller.app.vo.PreparePreOrderVo;
import cn.bmwm.modules.shop.entity.Cart;
import cn.bmwm.modules.shop.entity.CartItem;
import cn.bmwm.modules.shop.entity.PreOrder;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.CartService;
import cn.bmwm.modules.shop.service.PreOrderService;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * App -- 预约订单
 * @author zby
 * 2014-12-13 下午7:34:52
 */
@Controller
@RequestMapping(value = "/app/preorder")
public class PreOrderController {
	
	public static final Log log = LogFactory.getLog(PreOrderController.class);
	
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
	 * 订单中包含多个店铺的商品
	 */
	public static final int ORDER_MULTIPLE_SHOP = 103;
	
	/**
	 * 预约订单，联系人姓名为空
	 */
	private static final int ORDER_CONTACT_USER_EMPTY = 104;
	
	/**
	 * 预约订单，联系电话为空
	 */
	public static final int ORDER_CONTACT_PHONE_EMPTY = 105;
	
	/**
	 * 预约订单，人数为空
	 */
	public static final int ORDER_PERSONS_EMPTY = 106;
	
	/**
	 * 预约订单，预约时间为空
	 */
	public static final int ORDER_TIME_EMPTY = 107;
	
	/**
	 * 预约订单，预约时间格式错误
	 */
	public static final int ORDER_TIME_FORMAT_ERROR = 108;
	
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	
	@Resource(name = "preOrderServiceImpl")
	private PreOrderService preOrderService;

	
	/**
	 * 准备提交预约订单
	 * @return
	 */
	@RequestMapping(value = "/prepare", method = RequestMethod.POST)
	@ResponseBody
	public Result prepare(HttpSession session) {
		
		List<CartItemVo> cartItemList = new ArrayList<CartItemVo>();
		
		Cart cart = cartService.getAppCurrent();
		
		if (cart == null || cart.isEmpty()) {
			return new Result(ORDER_CART_EMPTY, 1, "购物车为空！");
		}
		
		Set<CartItem> selectedCartItems = cart.getSelectedCartItems();
		
		if(selectedCartItems == null || selectedCartItems.size() == 0) {
			return new Result(ORDER_CART_EMPTY, 1, "请选择需要下单的商品！");
		}
		
		Shop shop = null;
		
		for(CartItem item : selectedCartItems) {
			
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
			cartItem.setPrice(item.getUnitPrice());
			cartItem.setDiscountPrice(item.getSubtotal());
			cartItem.setQuantity(item.getQuantity());
			cartItem.setCartItemId(item.getId());
			cartItem.setIsSelected(item.getIsSelected());
			cartItem.setImageUrl(product.getImage());
			
			cartItemList.add(cartItem);
			
		}
		
		PreOrder preOrder = preOrderService.build(cart, "", null, null, "", "");
		
		Setting setting = SettingUtils.get();
		
		PreparePreOrderVo preparePreOrderVo = new PreparePreOrderVo();
		preparePreOrderVo.setShopName(shop.getName());
		preparePreOrderVo.setTotalPrice(preOrder.getTotalAmount());
		preparePreOrderVo.setPoints((long)(setting.getPointPercent() * cart.getPrice().doubleValue()));
		preparePreOrderVo.setCartItemList(cartItemList);
		
		return new Result(SUCCESS, 1, "", preparePreOrderVo);
		
	}
	
	/**
	 * 创建预约订单
	 * @param session
	 * @param memo
	 * @param contactUserName
	 * @param contactPhone
	 * @param persons
	 * @param time
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Result create(HttpSession session, String memo, String contactUserName, String contactPhone, Integer persons, String time) {
		
		if(StringUtils.isBlank(contactUserName)) {
			return new Result(ORDER_CONTACT_USER_EMPTY, 1, "联系人姓名为空！");
		}
		
		if(StringUtils.isBlank(contactPhone)) {
			return new Result(ORDER_CONTACT_PHONE_EMPTY, 1, "联系电话为空！");
		}
		
		if(persons == null) {
			return new Result(ORDER_PERSONS_EMPTY, 1, "预约人数为空！");
		}
		
		if(StringUtils.isBlank(time)) {
			return new Result(ORDER_TIME_EMPTY, 1, "预约时间为空！");
		}
		
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date bookTime = null;
		
		try {
			bookTime = sdf.parse(time);
		} catch (ParseException e) {
			log.error("预约时间格式错误！", e);
			return new Result(ORDER_TIME_FORMAT_ERROR, 1, "预约时间格式错误，2014-01-01 12:30");
		}
		
		preOrderService.create(cart, memo, bookTime, persons, contactUserName, contactPhone);
		
		return new Result(SUCCESS, 1);
		
	}
	
}
