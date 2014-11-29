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
 * 商品规格
 * @author zhoupuyue
 * @date 2014-11-10
 */
@Entity
@Table(name = "xx_product_specification")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_product_specification_sequence")
public class ProductSpecification extends BaseEntity {

	private static final long serialVersionUID = -6200444883007281113L;
	
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
	 * 获取商品规格
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}
	
	/**
	 * 获取商品规格值
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public SpecificationValue getSpecificationValue() {
		return specificationValue;
	}

	public void setSpecificationValue(SpecificationValue specificationValue) {
		this.specificationValue = specificationValue;
	}
	
}
