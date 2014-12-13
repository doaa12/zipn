/**
 * 
 */
package cn.bmwm.modules.shop.controller.app.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车 -- 商品
 * @author zhoupuyue
 * @date 2014-10-21
 */
public class CartItemVo {
	
	/**
	 * 商品ID
	 */
	private Long id;
	
	/**
	 * 购物车项ID
	 */
	private Long cartItemId;
	
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 商品价格
	 */
	private BigDecimal price;
	
	/**
	 * 折扣价
	 */
	private BigDecimal discountPrice;
	
	/**
	 * 商品图片地址
	 */
	private String imageUrl;
	
	/**
	 * 商品数量
	 */
	private int quantity;
	
	/**
	 * 该商品是否被选中
	 */
	private Boolean isSelected;
	
	/**
	 * 商品规格
	 */
	private List<String> specificationList = new ArrayList<String>();

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cartItemId
	 */
	public Long getCartItemId() {
		return cartItemId;
	}

	/**
	 * @param cartItemId the cartItemId to set
	 */
	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the discountPrice
	 */
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	/**
	 * @param discountPrice the discountPrice to set
	 */
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * @return the specification
	 */
	public List<String> getSpecificationList() {
		return specificationList;
	}

	/**
	 * @param specification the specification to set
	 */
	public void setSpecificationList(List<String> specificationList) {
		this.specificationList = specificationList;
	}
	
}
