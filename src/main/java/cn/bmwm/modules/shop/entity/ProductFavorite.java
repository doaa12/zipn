package cn.bmwm.modules.shop.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 商品收藏
 * @author zby
 * 2014-11-22 上午8:24:13
 */
@Entity
@Table(name = "xx_product_favorite")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_product_favorite_sequence")
public class ProductFavorite extends BaseEntity {

	private static final long serialVersionUID = 2642665362991043742L;
	
	/**
	 * 商品
	 */
	private Product product;
	
	/**
	 * 会员
	 */
	private Member member;
	
	/**
	 * 获取商品
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**
	 * 获取会员
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
}
