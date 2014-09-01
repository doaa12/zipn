package cn.bmwm.modules.shop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 商铺商品分类
 * @author zby
 * 2014-8-19 下午8:33:51
 */
@Entity
@Table(name = "xx_shop_category")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_shop_category_sequence")
public class ShopCategory extends OrderEntity {

	private static final long serialVersionUID = -3426075611070457220L;
	
	/**
	 * 商铺商品分类名称
	 */
	private String name;
	
	/** 
	 * 树路径
	 */
	private String treePath;
	
	/** 页面标题 */
	private String seoTitle;

	/** 页面关键词 */
	private String seoKeywords;

	/** 页面描述 */
	private String seoDescription;
	
	/**
	 * 商铺
	 */
	private Shop shop;
	
	/**
	 * 父分类
	 */
	private ProductCategory parent;
	
	/**
	 * 分类商品
	 */
	private Set<Product> products = new HashSet<Product>();
	
	/** 
	 * 促销
	 */
	private Set<Promotion> promotions = new HashSet<Promotion>();
	
	/** 
	 * 参数组 
	 */
	private Set<ParameterGroup> parameterGroups = new HashSet<ParameterGroup>();

	/** 
	 * 筛选属性 
	 */
	private Set<Attribute> attributes = new HashSet<Attribute>();
	
	/**
	 * 品牌
	 */
	private Set<Brand> brands = new HashSet<Brand>();
	
	
	/**
	 * 获取店铺分类名称
	 * @return
	 */
	@JsonProperty
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取树路径
	 * @return
	 */
	@Column(nullable = false)
	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	
	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	/**
	 * 获取店铺
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	/**
	 * 获取父分类
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public ProductCategory getParent() {
		return parent;
	}

	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}

	/**
	 * 获取店铺商品
	 * @return
	 */
	@OneToMany(mappedBy = "shopCategory", fetch = FetchType.LAZY)
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	/**
	 * 获取店铺促销
	 * @return
	 */
	@ManyToMany(mappedBy = "shopCategories", fetch = FetchType.LAZY)
	public Set<Promotion> getPromotions() {
		return promotions;
	}
	
	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}
	
	/**
	 * 获取参数组
	 * 
	 * @return 参数组
	 */
	@OneToMany(mappedBy = "shopCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	public Set<ParameterGroup> getParameterGroups() {
		return parameterGroups;
	}

	/**
	 * 设置参数组
	 * 
	 * @param parameterGroups
	 *            参数组
	 */
	public void setParameterGroups(Set<ParameterGroup> parameterGroups) {
		this.parameterGroups = parameterGroups;
	}
	
	/**
	 * 获取筛选属性
	 * 
	 * @return 筛选属性
	 */
	@OneToMany(mappedBy = "shopCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * 设置筛选属性
	 * 
	 * @param attributes
	 *            筛选属性
	 */
	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * 获取店铺商品分类品牌
	 * @return
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_shop_category_brand")
	public Set<Brand> getBrands() {
		return brands;
	}

	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Promotion> promotions = getPromotions();
		if (promotions != null) {
			for (Promotion promotion : promotions) {
				promotion.getShopCategories().remove(this);
			}
		}
	}
	
}
