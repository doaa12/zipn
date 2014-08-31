package cn.bmwm.modules.shop.controller.app.vo;

import java.io.Serializable;

/**
 * 店铺活动
 * @author zby
 * 2014-8-31 上午8:59:36
 */
public class ShopActivity implements Serializable {

	private static final long serialVersionUID = 488636177555967556L;
	
	/**
	 * 活动标识
	 */
	private Long code;
	
	/**
	 * 活动类型
	 */
	private Integer type;
	
	/**
	 * 活动标题
	 */
	private String title;
	
	/**
	 * 活动图片
	 */
	private String imageurl;

	
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	
}
