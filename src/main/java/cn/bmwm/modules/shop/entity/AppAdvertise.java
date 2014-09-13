package cn.bmwm.modules.shop.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * App首页广告
 * @author zby
 * 2014-9-13 上午9:08:15
 */
@Entity
@Table(name = "xx_app_advertise")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_app_advertise_sequence")
public class AppAdvertise extends BaseEntity {

	private static final long serialVersionUID = 5626163391841594183L;
	
	/**
	 * 广告区域
	 */
	private Area area;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 是否启用
	 */
	private Boolean isEnabled;
	
	/**
	 * 广告图片
	 */
	private String imageUrl;
	
	/**
	 * 广告链接地址
	 */
	private String linkUrl;
	
	/**
	 * 广告描述
	 */
	private String description;
	
	@OneToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
