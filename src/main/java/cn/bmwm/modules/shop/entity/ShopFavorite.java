package cn.bmwm.modules.shop.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 店铺收藏
 * @author zby
 * 2014-8-25 下午9:09:07
 */
@Entity
@Table(name = "xx_shop_favorite")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_shop_favorite_sequence")
public class ShopFavorite extends BaseEntity {

	private static final long serialVersionUID = 4133476178231345333L;
	
	/**
	 * 店铺
	 */
	private Shop shop;
	
	/**
	 * 会员
	 */
	private Member member;
	
	/**
	 * 获取店铺
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
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
