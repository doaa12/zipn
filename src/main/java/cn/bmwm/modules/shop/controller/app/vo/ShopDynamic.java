package cn.bmwm.modules.shop.controller.app.vo;

import java.util.List;

/**
 * 收藏店铺动态
 * @author zby
 * 2014-9-12 下午8:24:57
 */
public class ShopDynamic {
	
	/**
	 * 主键ID标识
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
	 * 店铺更新时间
	 */
	private String updateTime;
	
	/**
	 * 店铺发布新品
	 */
	private List<Item> list;

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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<Item> getList() {
		return list;
	}

	public void setList(List<Item> list) {
		this.list = list;
	}
	
}
