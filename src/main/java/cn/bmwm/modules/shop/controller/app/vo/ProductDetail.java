package cn.bmwm.modules.shop.controller.app.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 商品主页
 * @author zby
 * 2014-9-4 下午8:38:45
 */
public class ProductDetail implements Serializable {

	private static final long serialVersionUID = 7149445285842148347L;

	/**
	 * 主键标识
	 */
	private Long code;
	
	/**
	 * 商品标题
	 */
	private String title = "";
	
	/**
	 * 商品价格
	 */
	private Double price;
	
	/**
	 * 价格类型
	 */
	private String priceType = "";
	
	/**
	 * 商品图片
	 */
	private String imageurl = "";
	
	/**
	 * 评价次数
	 */
	private Long evaluateNumber;
	
	/**
	 * 评价分数
	 */
	private Long score;
	
	/**
	 * 商品图文详情
	 */
	private String descurl = "";
	
	/**
	 * 商品介绍
	 */
	private String desc = "";
	
	/**
	 * 店铺ID
	 */
	private Long storeId;
	
	/**
	 * 店铺名称
	 */
	private String storeName = "";
	
	/**
	 * 店铺图片
	 */
	private String storeImageUrl = "";
	
	/**
	 * 商品评价
	 */
	private List<Evaluate> evaluate;

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
		if(title != null){
			this.title = title;
		}
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		if(priceType != null) {
			this.priceType = priceType;
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

	public Long getEvaluateNumber() {
		return evaluateNumber;
	}

	public void setEvaluateNumber(Long evaluateNumber) {
		this.evaluateNumber = evaluateNumber;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public String getDescurl() {
		return descurl;
	}

	public void setDescurl(String descurl) {
		if(descurl != null) {
			this.descurl = descurl;
		}
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		if(desc != null) {
			this.desc = desc;
		}
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		if(storeName != null) {
			this.storeName = storeName;
		}
	}

	public String getStoreImageUrl() {
		return storeImageUrl;
	}

	public void setStoreImageUrl(String storeImageUrl) {
		if(storeImageUrl != null) {
			this.storeImageUrl = storeImageUrl;
		}
	}

	public List<Evaluate> getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(List<Evaluate> evaluate) {
		this.evaluate = evaluate;
	}

}
