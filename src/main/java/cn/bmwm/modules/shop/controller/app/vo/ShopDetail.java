package cn.bmwm.modules.shop.controller.app.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺主页
 * @author zby
 * 2014-8-31 上午8:55:27
 */
public class ShopDetail implements Serializable {

	private static final long serialVersionUID = -6323306968291996621L;
	
	/**
	 * 店铺标识
	 */
	private Long code;
	
	/**
	 * 店铺标题
	 */
	private String title;
	
	/**
	 * 店铺大图片
	 */
	private List<String> shopImages = new ArrayList<String>();
	
	/**
	 * 店铺头像图片
	 */
	private String headerImageurl = "";
	
	/**
	 * 店铺评分
	 */
	private Integer score;
	
	/**
	 * 是否收藏店铺，1：已收藏，0：未收藏
	 */
	private Integer collectFlag;
	
	/**
	 * 店铺商品数量
	 */
	private Long allProduct;
	
	/**
	 * 促销商品数量
	 */
	private Long saleProduct;
	
	/**
	 * 店铺状态，1:空闲，2：忙碌，3：火爆
	 */
	private Integer saleStatus;
	
	/**
	 * 店铺介绍
	 */
	private String desc = "";
	
	/**
	 * 店铺分类
	 */
	private String type = "";
	
	/**
	 * 店铺地址
	 */
	private String address;
	
	/**
	 * 店铺评论
	 */
	private String evaluate = "";
	
	/**
	 * 店铺公告
	 */
	private String notice = "";
	
	/**
	 * 店铺纬度
	 */
	private double latitude;
	
	/**
	 * 店铺经度
	 */
	private double longitude;
	
	/**
	 * 店铺活动
	 */
	private List<ShopActivity> activityList;
	
	/**
	 * 店铺热销商品
	 */
	private ItemCategory<Item> recommend;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getShopImages() {
		return shopImages;
	}

	public void setShopImages(List<String> shopImages) {
		this.shopImages = shopImages;
	}

	public String getHeaderImageurl() {
		return headerImageurl;
	}

	public void setHeaderImageurl(String headerImageurl) {
		if(headerImageurl != null) {
			this.headerImageurl = headerImageurl;
		}
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getCollectFlag() {
		return collectFlag;
	}

	public void setCollectFlag(Integer collectFlag) {
		this.collectFlag = collectFlag;
	}
	
	public Long getAllProduct() {
		return allProduct;
	}

	public void setAllProduct(Long allProduct) {
		this.allProduct = allProduct;
	}

	public Long getSaleProduct() {
		return saleProduct;
	}

	public void setSaleProduct(Long saleProduct) {
		this.saleProduct = saleProduct;
	}

	public Integer getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		if(desc != null) {
			this.desc = desc;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if(type != null) {
			this.type = type;
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if(address != null) {
			this.address = address;
		}
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		if(evaluate != null) {
			this.evaluate = evaluate;
		}
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		if(notice != null) {
			this.notice = notice;
		}
	}
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public List<ShopActivity> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<ShopActivity> activityList) {
		this.activityList = activityList;
	}

	public ItemCategory<Item> getRecommend() {
		return recommend;
	}

	public void setRecommend(ItemCategory<Item> recommend) {
		this.recommend = recommend;
	}
	
}
