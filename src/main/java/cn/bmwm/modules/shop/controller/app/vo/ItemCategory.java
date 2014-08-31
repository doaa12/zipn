package cn.bmwm.modules.shop.controller.app.vo;

import java.util.List;

/**
 * 分类VO
 * @author zby
 * 2014-8-29 下午9:06:46
 */
public class ItemCategory {

	/**
	 * 标识ID
	 */
	private Long code;
	
	/**
	 * 标题文字
	 */
	private String title;
	
	/**
	 * 更多类型，1：店铺，2：商品
	 */
	private Integer moretype;
	
	/**
	 * 是否显示更多
	 */
	private Integer showmore;
	
	/**
	 * 首页显示样式类型，1：店铺收藏动态，2：收藏店铺发布的新品，3：置顶店铺或者置顶商品，4：广告位
	 */
	private Integer showtype;
	
	/**
	 * 显示项列表
	 */
	private List<Item> dataList;

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

	public Integer getShowmore() {
		return showmore;
	}

	public void setShowmore(Integer showmore) {
		this.showmore = showmore;
	}
	
	public Integer getMoretype() {
		return moretype;
	}

	public void setMoretype(Integer moretype) {
		this.moretype = moretype;
	}

	public Integer getShowtype() {
		return showtype;
	}

	public void setShowtype(Integer showtype) {
		this.showtype = showtype;
	}

	public List<Item> getDataList() {
		return dataList;
	}

	public void setDataList(List<Item> dataList) {
		this.dataList = dataList;
	}
	
}
