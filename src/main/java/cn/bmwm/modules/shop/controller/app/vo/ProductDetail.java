package cn.bmwm.modules.shop.controller.app.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
	 * 商品销售价格
	 */
	private Double price;
	
	/**
	 * 商品原价
	 */
	private Double originalPrice;
	
	/**
	 * 价格类型
	 */
	private String priceType = "";
	
	/**
	 * 商品图片
	 */
	private List<String> imageList;
	
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
	private String introduction = "";
	
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
	 * 商家电话
	 */
	private String phone;
	
	/**
	 * 用户是否收藏该商品，1：已收藏，0：未收藏
	 */
	private Integer collectFlag;
	
	/**
	 * 库存
	 */
	private Integer stock;
	
	/**
	 * 商品评价
	 */
	private List<Evaluate> evaluate;
	
	/**
	 * 商品规格
	 */
	private List<Map<String,Object>> specifications;
	

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

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		if(priceType != null) {
			this.priceType = priceType;
		}
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
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
	
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		if(introduction != null) {
			this.introduction = introduction;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public List<Evaluate> getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(List<Evaluate> evaluate) {
		this.evaluate = evaluate;
	}
	
	public Integer getCollectFlag() {
		return collectFlag;
	}

	public void setCollectFlag(Integer collectFlag) {
		this.collectFlag = collectFlag;
	}

	public List<Map<String,Object>> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<Map<String,Object>> specifications) {
		this.specifications = specifications;
	}

}
