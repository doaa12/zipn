package cn.bmwm.modules.shop.controller.app.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 准备预约订单VO类
 * @author zby
 * 2014-12-14 上午11:35:52
 */
public class PreparePreOrderVo {
	
	/**
	 * 总价
	 */
	private BigDecimal totalPrice;
	
	/**
	 * 积分
	 */
	private long points;
	
	/**
	 * 店铺名称
	 */
	private String shopName;
	
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

	public List<CartItemVo> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItemVo> cartItemList) {
		this.cartItemList = cartItemList;
	}
	
}
