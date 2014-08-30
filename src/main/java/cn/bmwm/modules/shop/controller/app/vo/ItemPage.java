package cn.bmwm.modules.shop.controller.app.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 列表页面
 * @author zby
 * 2014-8-30 上午11:41:24
 */
public class ItemPage<T> implements Serializable {
	
	private static final long serialVersionUID = -6872481405850426548L;

	/**
	 * 页码
	 */
	private Integer page;
	
	/**
	 * 总行数
	 */
	private Long total;
	
	/**
	 * 每页显示行数
	 */
	private Integer size;
	
	/**
	 * 结果列表
	 */
	private List<T> list;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}
