package cn.bmwm.modules.shop.controller.app.vo;

/**
 * 显示项VO
 * @author zby
 * 2014-8-29 下午9:08:22
 */
public class Item {
	
	/**
	 * 标识ID
	 */
	private Long code;
	
	/**
	 * 类型，1：商铺，2：商品，3：商品列表，4：商铺列表
	 */
	private Integer type;
	
	/**
	 * 标题文字
	 */
	private String title;
	
	/**
	 * 图片URL
	 */
	private String imageurl;
	
	/**
	 * 商品价格
	 */
	private double price;
	
	/**
	 * 店铺所在区域
	 */
	private String area = "";
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

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

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
}
