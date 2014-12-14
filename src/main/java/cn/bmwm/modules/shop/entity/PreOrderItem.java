package cn.bmwm.modules.shop.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity -- 预约订单项
 * @author zby
 * 2014-12-13 下午9:57:04
 */
@Entity
@Table(name = "xx_preorder_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_preorder_item_sequence")
public class PreOrderItem extends BaseEntity {

	private static final long serialVersionUID = -3474980669871464050L;
	
	/** 商品编号 */
	private String sn;

	/** 商品名称 */
	private String name;

	/** 商品全称 */
	private String fullName;

	/** 商品价格 */
	private BigDecimal price;

	/** 商品缩略图 */
	private String thumbnail;

	/** 是否为赠品 */
	private Boolean isGift;

	/** 数量 */
	private Integer quantity;
	
	/**
	 * 重量
	 */
	private Integer weight;
	
	/** 商品 */
	private Product product;

	/** 预约订单 */
	private PreOrder preOrder;

	/**
	 * 获取商品编号
	 * @return
	 */
	@Column(nullable = false, updatable = false)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
	/**
	 * 获取商品名称
	 * @return
	 */
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取商品全称
	 * @return
	 */
	@Column(nullable = false)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	/**
	 * 获取商品价格
	 * @return
	 */
	@Column(nullable = false)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	/**
	 * 获取商品缩略图
	 * @return
	 */
	@Column(nullable = false)
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	/**
	 * 获取是否为赠品
	 * @return
	 */
	@Column(nullable = false)
	public Boolean getIsGift() {
		return isGift;
	}

	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}
	
	/**
	 * 获取商品数量
	 * @return
	 */
	@Column(nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 获取商品重量
	 * @return
	 */
	@Column(nullable = false)
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * 获取商品
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product", nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**
	 * 获取预约订单
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "preOrder", nullable = false, updatable = false)
	public PreOrder getPreOrder() {
		return preOrder;
	}

	public void setPreOrder(PreOrder preOrder) {
		this.preOrder = preOrder;
	}
	
}
