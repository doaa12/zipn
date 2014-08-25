package cn.bmwm.modules.shop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 商铺
 * @author zby
 * 2014-8-16 下午8:23:23
 */
@Entity
@Table(name = "xx_shop")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_shop_sequence")
public class Shop extends BaseEntity {

	public static final long serialVersionUID = -4279051580448402674L;
	
	/** 点击数缓存名称 */
	public static final String HITS_CACHE_NAME = "shopHits";
	
	/**	
	* 商铺名称
	*/
	private String name;
	
	/**
	 * 商铺简介
	 */
	private String description;
	
	/**
	 * 商铺状态，1:空闲，2：忙碌，3：火爆
	 */
	private Integer status;
	
	/**
	 * 商铺公告
	 */
	private String notice;
	
	/**
	 * 商铺支付账号
	 */
	private String payAccount;
	
	/**
	 * 商家
	 */
	private Admin admin;
	
	/**
	 * 店铺商品分类
	 */
	private Set<ShopCategory> shopCategories = new HashSet<ShopCategory>();
	
	/**
	 * 商铺商品
	 */
	private Set<Product> products = new HashSet<Product>();
	
	/**
	 * 收藏会员
	 */
	private Set<Member> favoriteMembers = new HashSet<Member>();
	
	/** 
	 * 促销
	 */
	private Set<Promotion> promotions = new HashSet<Promotion>();
	
	/**
	 * 店铺参数组
	 */
	private Set<ParameterGroup> parameterGroups = new HashSet<ParameterGroup>();
	
	/**
	 * 店铺属性
	 */
	private Set<Attribute> attributes = new HashSet<Attribute>();
	
	/**
	 * 规格
	 */
	private Set<Specification> specifications = new HashSet<Specification>();
	
	/**
	 * 店铺图片
	 */
	private Set<ShopImage> shopImages = new HashSet<ShopImage>();
	
	/**
	 * 店铺所在商品分类
	 */
	private ProductCategory productCategory;
	
	/**
	 * 是否置顶
	 */
	private Boolean isTop;
	
	/** 
	 * 是否列出 
	 */
	private Boolean isList;
	
	/**
	 * 店铺所在城市
	 */
	private String city;
	
	/**
	 * 店铺地址
	 */
	private String address;
	
	/**
	 * 店铺列表展示图片
	 */
	private String image;
	
	/**
	 * 店铺所在分类路径
	 */
	private String treePath;
	
	
	/**
	 * 获取店铺名称
	 * @return
	 */
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
	 * 获取店铺描述
	 * @return
	 */
	@Length(max = 500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 获取店铺状态
	 * @return
	 */
	@Column(nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * 获取店铺公告
	 * @return
	 */
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	/**
	 * 获取店铺支付账号
	 * @return
	 */
	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}
	
	/**
	 * 获取店铺管理员
	 * @return
	 */
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin", nullable = false)
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	/**
	 * 获取商品
	 * @return
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	/**
	 * 获取店铺分类
	 * @return
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<ShopCategory> getShopCategories() {
		return shopCategories;
	}

	public void setShopCategories(Set<ShopCategory> shopCategories) {
		this.shopCategories = shopCategories;
	}
	
	/**
	 * 获取收藏店铺的会员
	 * @return
	 */
	@ManyToMany(mappedBy = "favoriteShops", fetch = FetchType.LAZY)
	public Set<Member> getFavoriteMembers() {
		return favoriteMembers;
	}

	public void setFavoriteMembers(Set<Member> favoriteMembers) {
		this.favoriteMembers = favoriteMembers;
	}
	
	/**
	 * 获取店铺促销
	 * @return
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}
	
	/**
	 * 获取店铺参数组
	 * @return
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<ParameterGroup> getParameterGroups() {
		return parameterGroups;
	}

	public void setParameterGroups(Set<ParameterGroup> parameterGroups) {
		this.parameterGroups = parameterGroups;
	}
	
	/**
	 * 获取店铺属性
	 * @return
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * 商品规格
	 * @return
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	public Set<Specification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(Set<Specification> specifications) {
		this.specifications = specifications;
	}
	
	/**
	 * @return the shopImages
	 */
	@Valid
	@ElementCollection
	@CollectionTable(name = "xx_shop_shop_image")
	public Set<ShopImage> getShopImages() {
		return shopImages;
	}

	/**
	 * @param shopImages the shopImages to set
	 */
	public void setShopImages(Set<ShopImage> shopImages) {
		this.shopImages = shopImages;
	}

	/**
	 * 获取店铺商品分类
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	
	/**
	 * 获取是否置顶
	 * @return
	 */
	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}
	
	/**
	 * 获取是否列出
	 * @return
	 */
	public Boolean getIsList() {
		return isList;
	}

	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取店铺所在城市
	 * @return
	 */
	@NotEmpty
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * 获取店铺地址
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取店铺商品分类路径
	 * @return
	 */
	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	
}
