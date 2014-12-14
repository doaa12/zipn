package cn.bmwm.modules.shop.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bmwm.modules.shop.dao.CartItemDao;
import cn.bmwm.modules.shop.dao.PreOrderDao;
import cn.bmwm.modules.shop.dao.PreOrderLogDao;
import cn.bmwm.modules.shop.dao.ProductDao;
import cn.bmwm.modules.shop.dao.SnDao;
import cn.bmwm.modules.shop.entity.Cart;
import cn.bmwm.modules.shop.entity.CartItem;
import cn.bmwm.modules.shop.entity.PreOrder;
import cn.bmwm.modules.shop.entity.PreOrder.PaymentStatus;
import cn.bmwm.modules.shop.entity.PreOrder.PreOrderStatus;
import cn.bmwm.modules.shop.entity.PreOrderItem;
import cn.bmwm.modules.shop.entity.PreOrderLog;
import cn.bmwm.modules.shop.entity.PreOrderLog.Type;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.Promotion;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.Sn;
import cn.bmwm.modules.shop.service.PreOrderService;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.model.Setting.StockAllocationTime;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * Service  -- 预约订单
 * @author zby
 * 2014-12-14 上午10:10:39
 */
@Service("preOrderServiceImpl")
public class PreOrderServiceImpl extends BaseServiceImpl<PreOrder,Long> implements PreOrderService {
	
	@Resource(name = "preOrderDaoImpl")
	private PreOrderDao preOrderDao;
	
	@Resource(name = "cartItemDaoImpl")
	private CartItemDao cartItemDao;
	
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	
	@Resource(name = "preOrderLogDaoImpl")
	private PreOrderLogDao preOrderLogDao;
	
	
	/**
	 * 生成预约订单 
	 * @param cart
	 * @param memo
	 * @param bookTime
	 * @param persons
	 * @param contactUserName
	 * @param contactPhone
	 * @return
	 */
	public PreOrder build(Cart cart, String memo, Date bookTime, Integer persons, String contactUserName, String contactPhone) {
		
		PreOrder preOrder = new PreOrder();
		
		Setting setting = SettingUtils.get();
		
		preOrder.setTotalAmount(cart.getPrice());
		preOrder.setMemo(memo);
		preOrder.setMember(cart.getMember());
		preOrder.setPoint((long)(setting.getPointPercent() * cart.getPrice().doubleValue()));
		preOrder.setPaymentMethodName("在线支付");
		
		preOrder.setBookTime(bookTime);
		preOrder.setPersons(persons);
		preOrder.setContactUserName(contactUserName);
		preOrder.setContactPhone(contactPhone);

		if (!cart.getPromotions().isEmpty()) {
			StringBuffer promotionName = new StringBuffer();
			for (Promotion promotion : cart.getPromotions()) {
				if (promotion != null && promotion.getName() != null) {
					promotionName.append(" " + promotion.getName());
				}
			}
			if (promotionName.length() > 0) {
				promotionName.deleteCharAt(0);
			}
			preOrder.setPromotion(promotionName.toString());
		}
		
		Shop shop = null;
		
		List<PreOrderItem> preOrderItems = preOrder.getPreOrderItems();
		
		for (CartItem cartItem : cart.getCartItems()) {
			if (cartItem != null && cartItem.getProduct() != null) {
				Product product = cartItem.getProduct();
				if(shop == null) {
					shop = product.getShop();
				}
				PreOrderItem preOrderItem = new PreOrderItem();
				preOrderItem.setSn(product.getSn());
				preOrderItem.setName(product.getName());
				preOrderItem.setFullName(product.getFullName());
				preOrderItem.setPrice(cartItem.getUnitPrice());
				preOrderItem.setWeight(cartItem.getWeight());
				preOrderItem.setThumbnail(product.getImage());
				preOrderItem.setIsGift(false);
				preOrderItem.setQuantity(cartItem.getQuantity());
				preOrderItem.setProduct(product);
				preOrderItem.setPreOrder(preOrder);
				preOrderItems.add(preOrderItem);
			}
		}
		
		preOrder.setAmountPaid(new BigDecimal(0));
		preOrder.setShop(shop);

		preOrder.setPreOrderStatus(PreOrderStatus.unconfirmed);
		preOrder.setPaymentStatus(PaymentStatus.unpaid);

		preOrder.setExpire(DateUtils.addMinutes(new Date(), setting.getOrderExpireTime()));
		
		return preOrder;
		
	}
	
	/**
	 * 创建预约订单
	 * @param cart
	 * @param receiver
	 * @param memo
	 * @return
	 */
	@Transactional
	public PreOrder create(Cart cart, String memo, Date bookTime, Integer persons, String contactUserName, String contactPhone) {
		
		PreOrder preOrder = build(cart, memo, bookTime, persons, contactUserName, contactPhone);

		preOrder.setSn(snDao.generate(Sn.Type.order));
		
		preOrder.setLockExpire(DateUtils.addSeconds(new Date(), 20));

		Setting setting = SettingUtils.get();
		
		if (setting.getStockAllocationTime() == StockAllocationTime.order) {
			preOrder.setIsAllocatedStock(true);
		} else {
			preOrder.setIsAllocatedStock(false);
		}

		preOrderDao.persist(preOrder);

		PreOrderLog preOrderLog = new PreOrderLog();
		preOrderLog.setType(Type.create);
		preOrderLog.setOperator(null);
		preOrderLog.setPreOrder(preOrder);
		preOrderLogDao.persist(preOrderLog);

		if (setting.getStockAllocationTime() == StockAllocationTime.order) {
			for (PreOrderItem preOrderItem : preOrder.getPreOrderItems()) {
				if (preOrderItem != null) {
					Product product = preOrderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() + preOrderItem.getQuantity());
						productDao.merge(product);
						preOrderDao.flush();
					}
				}
			}
		}
		
		for(CartItem cartItem : cart.getSelectedCartItems()) {
			cartItemDao.remove(cartItem);
		}
		
		return preOrder;
		
	}

}
