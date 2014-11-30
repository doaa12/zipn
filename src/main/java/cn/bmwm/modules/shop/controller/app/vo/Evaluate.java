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
	
	/**
	 * 评论时间
	 */
	private String time = "";
	
	/**
	 * 点评图片1
	 */
	private String image1 = "";
	
	/**
	 * 点评图片2
	 */
	private String image2 = "";
	
	/**
	 * 点评图片3
	 */
	private String image3 = "";
	
	/**
	 * 点评图片4
	 */
	private String image4 = "";
	
	/**
	 * 点评图片5
	 */
	private String image5 = "";

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		if(image1 == null || image1.equals("")) return;
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		if(image2 == null || image2.equals("")) return;
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		if(image3 == null || image3.equals("")) return;
		this.image3 = image3;
	}

	public String getImage4() {
		return image4;
	}

	public void setImage4(String image4) {
		if(image4 == null || image4.equals("")) return;
		this.image4 = image4;
	}

	public String getImage5() {
		return image5;
	}

	public void setImage5(String image5) {
		if(image5 == null || image5.equals("")) return;
		this.image5 = image5;
	}
	
}
