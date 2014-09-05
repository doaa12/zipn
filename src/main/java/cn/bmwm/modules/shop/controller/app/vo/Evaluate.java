package cn.bmwm.modules.shop.controller.app.vo;

import java.io.Serializable;

/**
 * 商品评价
 * @author zby
 * 2014-9-4 下午9:38:50
 */
public class Evaluate implements Serializable {

	private static final long serialVersionUID = 8267829488917294846L;
	
	/**
	 * 评价ID
	 */
	private Long id;
	
	/**
	 * 评价人用户名
	 */
	private String name = "";
	
	/**
	 * 评分
	 */
	private Integer score;
	
	/**
	 * 评价内容
	 */
	private String desc = "";

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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		if(desc != null) {
			this.desc = desc;
		}
	}
	
}
