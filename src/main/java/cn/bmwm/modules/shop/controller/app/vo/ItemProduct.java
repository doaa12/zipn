package cn.bmwm.modules.shop.controller.app.vo;

/**
 * 商品列表VO类
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
	private String name = "";
	
	/**
	 * 商品图片
	 */
	private String imageurl = "";
	
	/**
	 * 商品价格
	 */
	private double price;
	
	/**
	 * 原价
	 */
	private double originalPrice;
	
	/**
	 * 商品评论数
	 */
	private Long evaluateCount;
	
	/**
	 * 商品属性1
	 */
	private String attribute1 = "";
	
	/**
	 * 商品属性2
	 */
	private String attribute2 = "";

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
		if(name != null) {
			this.name = name;
		}
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		if(imageurl != null) {
			this.imageurl = imageurl;
		}
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
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
		if(attribute1 != null) {
			this.attribute1 = attribute1;
		}
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		if(attribute2 != null) {
			this.attribute2 = attribute2;
		}
	}
	
}
