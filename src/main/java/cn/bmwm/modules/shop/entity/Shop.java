package cn.bmwm.modules.shop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Similarity;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKSimilarity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 商铺
 * @author zby
 * 2014-8-16 下午8:23:23
 */
@Indexed
@Similarity(impl = IKSimilarity.class)
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
	 * 店铺图片
	 */
	private List<ShopImage> shopImages = new ArrayList<ShopImage>();
	
	/**
	 * 虚拟分类,用于首页归类店铺推荐
	 */
	private List<VirtualShopCategory> virtualCategories = new ArrayList<VirtualShopCategory>();
	
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
	 * 店铺列表图片URL
	 */
	private String image;
	
	/**
	 * 店铺Logo图片URL
	 */
	private String logo;
	
	/**
	 * 店铺收藏数量
	 */
	private Long favoriteCount;
	
	/**
	 * 类目名称
	 */
	private String categoryName;
	
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
	 * 店铺经营范围
	 */
	private String shopType;
	
	/**
	 * 平均价格
	 */
	private Double avgPrice;
	
	/**
	 * 店铺纬度
	 */
	private BigDecimal latitude;
	
	/**
	 * 店铺经度
	 */
	private BigDecimal longitude;
	
	/**
	 * 电话
	 */
	private String telephone;
	
	/**
	 * 获取店铺名称
	 * @return
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@NotEmpty
	@Length(max = 200)
	@Column(length = 100, nullable = false)
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
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@Length(max = 500)
	@Column(length = 500)
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
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
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
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@Column(length = 500)
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
	@Column(length = 50)
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
	@OrderBy("order asc")
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
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
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
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	public Boolean getIsList() {
		return isList;
	}

	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	/**
	 * 获取店铺图片
	 * @return
	 */
	@Valid
	@ElementCollection
	@CollectionTable(name = "xx_shop_shop_image")
	public List<ShopImage> getShopImages() {
		return shopImages;
	}

	public void setShopImages(List<ShopImage> shopImages) {
		this.shopImages = shopImages;
	}
	
	/**
	 * @return the virtualCategories
	 */
	@ManyToMany(mappedBy = "shops", fetch = FetchType.LAZY)
	public List<VirtualShopCategory> getVirtualCategories() {
		return virtualCategories;
	}

	/**
	 * @param virtualCategories the virtualCategories to set
	 */
	public void setVirtualCategories(List<VirtualShopCategory> virtualCategories) {
		this.virtualCategories = virtualCategories;
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
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
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
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
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
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 获取店铺列表图片
	 * @return
	 */
	@Field(store = Store.YES, index = Index.NO)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * @return the favoriteCount
	 */
	@Field(store = Store.YES, index = Index.NO)
	public Long getFavoriteCount() {
		return favoriteCount;
	}

	/**
	 * @param favoriteCount the favoriteCount to set
	 */
	public void setFavoriteCount(Long favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	
	/**
	 * 店铺所在类目
	 * @return
	 */
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * 总评分数
	 * @return
	 */
	@Field(store = Store.YES, index = Index.NO)
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
	@Field(store = Store.YES, index = Index.NO)
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
	
	/**
	 * 店铺经营范围
	 * @return
	 */
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	/**
	 * 人均消费价格
	 * @return
	 */
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	public Double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}
	
	@Column(nullable = false, precision = 21, scale = 15)
	@Field(store = Store.YES, index = Index.NO)
	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	
	@Field(store = Store.YES, index = Index.NO)
	@Column(nullable = false, precision = 21, scale = 15)
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String toString() {
		return this.getId().toString();
	}
	
}
