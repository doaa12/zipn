/**
 * 
 */
package cn.bmwm.modules.shop.controller.app.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车 -- 店铺
 * @author zhoupuyue
 * @date 2014-10-21
 */
public class CartShop {
	
	/**
	 * 店铺名称
	 */
	private String shopName;
	
	/**
	 * 店铺活动
	 */
	private String shopActivity;
	
	/**
	 * 总价
	 */
	private BigDecimal totalPrice;
	
	/**
	 * 商品
	 */
	private List<CartProduct> productList;

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the shopActivity
	 */
	public String getShopActivity() {
		return shopActivity;
	}

	/**
	 * @param shopActivity the shopActivity to set
	 */
	public void setShopActivity(String shopActivity) {
		this.shopActivity = shopActivity;
	}

	/**
	 * @return the totalPrice
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the productList
	 */
	public List<CartProduct> getProductList() {
		return productList;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<CartProduct> productList) {
		this.productList = productList;
	}
	
}
