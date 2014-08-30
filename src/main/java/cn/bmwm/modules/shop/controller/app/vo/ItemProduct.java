package cn.bmwm.modules.shop.controller.app.vo;

/**
 * 商品VO类
 * @author zby
 * 2014-8-30 下午4:55:31
 */
public class ItemProduct {
	
	/**
	 * 商品ID
	 */
	private Long id;
	
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 商品图片
	 */
	private String imageurl;
	
	/**
	 * 商品价格
	 */
	private double price;
	
	/**
	 * 商品评论数
	 */
	private Long evaluateCount;
	
	/**
	 * 商品属性1
	 */
	private String attribute1;
	
	/**
	 * 商品属性2
	 */
	private String attribute2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getEvaluateCount() {
		return evaluateCount;
	}

	public void setEvaluateCount(Long evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
	
}
