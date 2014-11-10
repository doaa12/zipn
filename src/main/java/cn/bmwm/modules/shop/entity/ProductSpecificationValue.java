/**
 * 
 */
package cn.bmwm.modules.shop.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 商品规格值
 * @author zhoupuyue
 * @date 2014-11-10
 */
@Entity
@Table(name = "xx_product_specification_value")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_product_specification_value_sequence")
public class ProductSpecificationValue extends BaseEntity {

	private static final long serialVersionUID = -3007075588990206597L;
	
	/**
	 * 商品
	 */
	private Product product;
	
	/**
	 * 商品规格
	 */
	private Specification specification;
	
	/**
	 * 商品规格值
	 */
	private SpecificationValue specificationValue;
	
	/**
	 * 商品规格实例
	 */
	private ProductSpecification productSpecification;

	/**
	 * @return the product
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the specification
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Specification getSpecification() {
		return specification;
	}

	/**
	 * @param specification the specification to set
	 */
	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	/**
	 * @return the specificationValue
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public SpecificationValue getSpecificationValue() {
		return specificationValue;
	}

	/**
	 * @param specificationValue the specificationValue to set
	 */
	public void setSpecificationValue(SpecificationValue specificationValue) {
		this.specificationValue = specificationValue;
	}

	/**
	 * @return the productSpecification
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public ProductSpecification getProductSpecification() {
		return productSpecification;
	}

	/**
	 * @param productSpecification the productSpecification to set
	 */
	public void setProductSpecification(ProductSpecification productSpecification) {
		this.productSpecification = productSpecification;
	}

}
