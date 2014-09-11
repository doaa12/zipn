/**
 * 
 */
package cn.bmwm.modules.shop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 店铺虚拟分类,用于首页归类显示
 * @author zhoupuyue
 * @date 2014-9-11
 */
@Entity
@Table(name = "xx_virtual_shop_category")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_virtual_shop_category_sequence")
public class VirtualShopCategory extends OrderEntity {

	private static final long serialVersionUID = 181371570609399856L;

	/**
	 * 分类名称
	 */
	private String name;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 虚拟分类区域
	 */
	private Area area;
	
	/**
	 * 分类店铺
	 */
	private Set<Shop> shops = new HashSet<Shop>();
	
	/**
	 * 是否选择
	 */
	private Boolean isSelected = false;

	/**
	 * @return the name
	 */
	@NotEmpty
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
	@NotEmpty
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
	@ManyToOne(fetch = FetchType.LAZY)
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
	 * @return the shops
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Shop> getShops() {
		return shops;
	}

	/**
	 * @param shops the shops to set
	 */
	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}

	/**
	 * @return the isSelected
	 */
	@Transient
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}
