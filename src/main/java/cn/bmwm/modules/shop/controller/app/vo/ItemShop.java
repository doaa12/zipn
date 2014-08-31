package cn.bmwm.modules.shop.controller.app.vo;

import java.io.Serializable;

/**
 * 店铺列表VO类
 * @author zby
 * 2014-8-31 上午10:29:21
 */
public class ItemShop implements Serializable {
	
	private static final long serialVersionUID = 1741168952210947777L;
	
	/**
	 * 店铺标识
	 */
	private Long code;
	
	/**
	 * 店铺标题
	 */
	private String title;
	
	/**
	 * 距离
	 */
	private String distance;
	
	/**
	 * 图片
	 */
	private String imageurl;
	
	/**
	 * 评分
	 */
	private Integer score;
	
	/**
	 * 状态，1:空闲，2：忙碌，3：火爆
	 */
	private Integer status;
	
	/**
	 * 平局价格
	 */
	private Integer price;
	
	/**
	 * 区域
	 */
	private String area;
	
	/**
	 * 店铺类型，餐厅等
	 */
	private String type = "";
	
	/**
	 * 店铺类别
	 */
	private String category;


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

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
