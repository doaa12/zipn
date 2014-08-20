package cn.bmwm.modules.shop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
	@Column(nullable = false)
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
	
}
