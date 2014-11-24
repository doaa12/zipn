/**
 * 
 */
package cn.bmwm.modules.shop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.IndexColumn;

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
	 * 已分配库存
	 */
	private Integer allocatedStock;
	
	/**
	 * 商品
	 */
	private Product product;
	
	/**
	 * 商品价格
	 */
	private BigDecimal price;
	
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
	 * @return the allocatedStock
	 */
	public Integer getAllocatedStock() {
		return allocatedStock;
	}

	/**
	 * @param allocatedStock the allocatedStock to set
	 */
	public void setAllocatedStock(Integer allocatedStock) {
		this.allocatedStock = allocatedStock;
	}
	
	/**
	 * 获取可用库存
	 * @return
	 */
	@Transient
	public Integer getAvailableStock() {
		Integer availableStock = null;
		if (getStock() != null && getAllocatedStock() != null) {
			availableStock = getStock() - getAllocatedStock();
			if (availableStock < 0) {
				availableStock = 0;
			}
		}
		return availableStock;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the productSpecificationValues
	 */
	@OneToMany(mappedBy = "productSpecification", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderColumn(name = "orders")
	@IndexColumn(name = "orders")
	public List<ProductSpecificationValue> getProductSpecificationValues() {
		return productSpecificationValues;
	}

	/**
	 * @param productSpecificationValues the productSpecificationValues to set
	 */
	public void setProductSpecificationValues(List<ProductSpecificationValue> productSpecificationValues) {
		this.productSpecificationValues = productSpecificationValues;
	}
	
	/**
	 * 获取商品规格
	 * @return
	 */
	@Transient
	public Set<Specification> getSpecifications() {
		
		Set<Specification> specifications = new HashSet<Specification>();
		
		if(productSpecificationValues == null || productSpecificationValues.size() == 0) return specifications;
		
		for(ProductSpecificationValue productSpecificationValue : productSpecificationValues) {
			specifications.add(productSpecificationValue.getSpecification());
		}
		
		return specifications;
			
	}
	
	/**
	 * 获取商品规格值
	 * @return
	 */
	@Transient
	public Set<SpecificationValue> getSpecificationValues() {
		
		Set<SpecificationValue> specificationValues = new HashSet<SpecificationValue>();
		
		if(productSpecificationValues == null || productSpecificationValues.size() == 0) return specificationValues;
		
		for(ProductSpecificationValue productSpecificationValue : productSpecificationValues) {
			specificationValues.add(productSpecificationValue.getSpecificationValue());
		}
		
		return specificationValues;
		
	}
	
	public void prepareOrders() {
		for(int i = 0 ; i < productSpecificationValues.size(); i++ ) {
			ProductSpecificationValue value = productSpecificationValues.get(i);
			value.setOrders(i);
		}
	}
	
}
