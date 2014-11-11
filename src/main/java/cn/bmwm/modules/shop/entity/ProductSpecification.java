/**
 * 
 */
package cn.bmwm.modules.shop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
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
	 * 库存
	 */
	private Integer stock;
	
	/**
	 * 商品
	 */
	private Product product;
	
	/**
	 * 商品规格值
	 */
	private List<ProductSpecificationValue> productSpecificationValues = new ArrayList<ProductSpecificationValue>();

	/**
	 * @return the stock
	 */
	public Integer getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}

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
	 * @return the productSpecificationValues
	 */
	@OneToMany(mappedBy = "productSpecification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OrderColumn(name = "orders")
	public List<ProductSpecificationValue> getProductSpecificationValues() {
		return productSpecificationValues;
	}

	/**
	 * @param productSpecificationValues the productSpecificationValues to set
	 */
	public void setProductSpecificationValues(
			List<ProductSpecificationValue> productSpecificationValues) {
		this.productSpecificationValues = productSpecificationValues;
	}
	
}
