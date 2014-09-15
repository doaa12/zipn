package cn.bmwm.modules.shop.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 店铺活动
 * @author zby
 * 2014-9-15 下午8:53:19
 */
@Entity
@Table(name = "xx_shop_activity")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_shop_activity_sequence")
public class ShopActivity extends OrderEntity {

	private static final long serialVersionUID = 4572476284302889010L;
	
	/**
	 * 活动图片
	 */
	private String imageurl;
	
	/**
	 * 链接URL
	 */
	private String linkurl;
	
	/**
	 * 店铺
	 */
	private Shop shop;

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
}
