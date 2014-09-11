/**
 * 
 */
package cn.bmwm.modules.shop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 商品虚拟分类,用于首页归类显示
 * @author zhoupuyue
 * @date 2014-9-11
 */
@Entity
@Table(name = "xx_virtual_product_category")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_virtual_product_category_sequence")
public class VirtualProductCategory extends OrderEntity {

	private static final long serialVersionUID = -4070125800747842293L;
	
	/**
	 * 虚拟分类名称
	 */
	private String name;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 虚拟分类所属的区域
	 */
	private Area area;
	
	/**
	 * 分类商品
	 */
	private Set<Product> products = new HashSet<Product>();

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
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * @return the products
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
}
