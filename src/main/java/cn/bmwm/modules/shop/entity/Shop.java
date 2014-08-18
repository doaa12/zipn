package cn.bmwm.modules.shop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
	 * 商铺类目
	 */
	private ProductCategory shopCategory;
	
	/**
	 * 商家
	 */
	private Admin owner;
	
	/**
	 * 商铺商品
	 */
	private Set<Product> products = new HashSet<Product>();
	
	/**
	 * 收藏会员
	 */
	private Set<Member> favoriteMembers = new HashSet<Member>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public ProductCategory getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(ProductCategory shopCategory) {
		this.shopCategory = shopCategory;
	}
	
	public Admin getOwner() {
		return owner;
	}

	public void setOwner(Admin owner) {
		this.owner = owner;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Set<Member> getFavoriteMembers() {
		return favoriteMembers;
	}

	public void setFavoriteMembers(Set<Member> favoriteMembers) {
		this.favoriteMembers = favoriteMembers;
	}
	
}
