/*


 * */
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

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 规格值
 * 
 *
 * @version 1.0
 */
@Entity
@Table(name = "xx_specification_value")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_specification_val_sequence")
public class SpecificationValue extends OrderEntity {

	private static final long serialVersionUID = -8624741017444123488L;

	/** 名称 */
	private String name;

	/** 图片 */
	private String image;

	/** 规格 */
	private Specification specification;
	
	/**
	 * 商品规格值
	 */
	private Set<ProductSpecificationValue> productSpecificationValues = new HashSet<ProductSpecificationValue>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取图片
	 * 
	 * @return 图片
	 */
	@Length(max = 200)
	public String getImage() {
		return image;
	}

	/**
	 * 设置图片
	 * 
	 * @param image 图片
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取规格
	 * 
	 * @return 规格
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Specification getSpecification() {
		return specification;
	}

	/**
	 * 设置规格
	 * 
	 * @param specification
	 *            规格
	 */
	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	/**
	 * @return the productSpecificationValues
	 */
	@OneToMany(mappedBy = "specificationValue", fetch = FetchType.LAZY)
	public Set<ProductSpecificationValue> getProductSpecificationValues() {
		return productSpecificationValues;
	}

	/**
	 * @param productSpecificationValues the productSpecificationValues to set
	 */
	public void setProductSpecificationValues(
			Set<ProductSpecificationValue> productSpecificationValues) {
		this.productSpecificationValues = productSpecificationValues;
	}
	
}