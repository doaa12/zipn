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
	 * 活动图片
	 */
	private String imageurl;
	
	/**
	 * 活动链接
	 */
	private String linkurl;

	
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
	
}
