package cn.bmwm.modules.shop.controller.app.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 准备订单VO类
 * @author zby
 * 2014-12-11 下午10:01:24
 */
public class PrepareOrderVo {
	
	/**
	 * 总价
	 */
	private BigDecimal totalPrice;
	
	/**
	 * 运费
	 */
	private BigDecimal freight;
	
	/**
	 * 积分
	 */
	private long points;
	
	/**
	 * 店铺名称
	 */
	private String shopName;
	
	/**
	 * 收货人详细地址
	 */
	private String receiverAddress;
	
	/**
	 * 收货人姓名
	 */
	private String receiverUserName;
	
	/**
	 * 收货人电话
	 */
	private String receiverPhone;
	
	/**
	 * 购物车信息
	 */
	private List<CartItemVo> cartItemList;

	

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverUserName() {
		return receiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public List<CartItemVo> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItemVo> cartItemList) {
		this.cartItemList = cartItemList;
	}
	
}
