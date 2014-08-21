package cn.bmwm.modules.shop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

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
	 * 获取店铺分类名称
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

	/**
	 * 获取店铺
	 * @return
	 */
	@NotNull
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
	@OneToMany(mappedBy = "shopCategory", fetch = FetchType.LAZY)
	public Set<Promotion> getPromotions() {
		return promotions;
	}
	
	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}
	
}
