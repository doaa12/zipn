package cn.bmwm.modules.shop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	private Set<ShopFavorite> shopFavorite = new HashSet<ShopFavorite>();
	
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
	 * 店铺评论
	 */
	private Set<ShopReview> reviews = new HashSet<ShopReview>();
	
	/**
	 * 店铺所在商品分类
	 */
	private ProductCategory productCategory;
	
	/**
	 * 店铺所在区域
	 */
	private Area area;
	
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
	 * 店铺所在区域
	 */
	private String region;
	
	/**
	 * 店铺地址
	 */
	private String address;
	
	/**
	 * 店铺图片
	 */
	private String image;
	
	/**
	 * 店铺Logo
	 */
	private String logo;
	
	/**
	 * 总评分数
	 */
	private Long totalScore;
	
	/**
	 * 评分次数
	 */
	private Long scoreTimes;
	
	/**
	 * 店铺所在分类路径
	 */
	private String treePath;
	
	/**
	 * 店铺类型，比如餐厅
	 */
	private String shopType;
	
	/**
	 * 平均价格
	 */
	private Integer avgPrice;
	
	/**
	 * 店铺经度
	 */
	private Integer longitude;
	
	/**
	 * 店铺纬度
	 */
	private Integer latitude;
	
	/**
	 * 获取店铺名称
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
	 * 获取店铺描述
	 * @return
	 */
	@JsonProperty
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
	@JsonProperty
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
	@JsonProperty
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
	 * 获取收藏会员
	 * @return
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	public Set<ShopFavorite> getShopFavorite() {
		return shopFavorite;
	}

	public void setShopFavorite(Set<ShopFavorite> shopFavorite) {
		this.shopFavorite = shopFavorite;
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
	 * 店铺评论
	 * @return
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	public Set<ShopReview> getReviews() {
		return reviews;
	}

	public void setReviews(Set<ShopReview> reviews) {
		this.reviews = reviews;
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
	 * 店铺所在区域
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
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
	@JsonProperty
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	/**
	 * 获取店铺所在区域
	 * @return
	 */
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * 获取店铺所在城市
	 * @return
	 */
	@JsonProperty
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
	@JsonProperty
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 总评分数
	 * @return
	 */
	public Long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * 评分次数
	 * @return
	 */
	public Long getScoreTimes() {
		if(scoreTimes == null) return 0L;
		return scoreTimes;
	}

	public void setScoreTimes(Long scoreTimes) {
		this.scoreTimes = scoreTimes;
	}
	
	/**
	 * 平均评分
	 * @return
	 */
	@Transient
	public int getAvgScore() {
		if(scoreTimes == null || scoreTimes == 0) return 5;
		return (int)(totalScore / scoreTimes);
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
	
	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	/**
	 * 平均价格
	 * @return
	 */
	public Integer getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Integer avgPrice) {
		this.avgPrice = avgPrice;
	}
	
	public Integer getLongitude() {
		return longitude;
	}

	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
	}

	public Integer getLatitude() {
		return latitude;
	}

	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
	}

	public static void main(String[] args) {
		long total = 10;
		long times = 3;
		double d = total / (times * 1.0);
		d = Math.round(d * 10) / 10.0;
		System.out.println(d);
	}
	
}
