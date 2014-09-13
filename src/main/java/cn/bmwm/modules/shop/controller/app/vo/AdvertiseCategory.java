package cn.bmwm.modules.shop.controller.app.vo;

/**
 * 首页广告
 * @author zby
 * 2014-9-13 下午3:48:30
 */
public class AdvertiseCategory {

	/**
	 * 首页显示样式类型，1：店铺收藏动态，2：收藏店铺发布的新品，3：置顶店铺或者置顶商品，4：广告位
	 */
	private Integer showtype;
	
	/**
	 * 是否显示更多
	 */
	private Integer showmore;
	
	/**
	 * 图片地址
	 */
	private String imageurl;
	
	/**
	 * 链接地址
	 */
	private String linkurl;

	public Integer getShowtype() {
		return showtype;
	}

	public void setShowtype(Integer showtype) {
		this.showtype = showtype;
	}

	public Integer getShowmore() {
		return showmore;
	}

	public void setShowmore(Integer showmore) {
		this.showmore = showmore;
	}

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
