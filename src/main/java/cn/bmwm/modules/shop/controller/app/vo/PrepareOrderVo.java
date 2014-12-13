package cn.bmwm.modules.shop.controller.app.vo;

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
	private double totalPrice;
	
	/**
	 * 积分
	 */
	private int points;
	
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
	private List<CartProduct> cartItemList;

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
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

	public List<CartProduct> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartProduct> cartItemList) {
		this.cartItemList = cartItemList;
	}
	
}
