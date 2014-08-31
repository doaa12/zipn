package cn.bmwm.modules.shop.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 店铺评论
 * @author zby
 * 2014-8-31 上午9:46:03
 */
@Entity
@Table(name = "xx_shop_review")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_shop_review_sequence")
public class ShopReview extends BaseEntity {

	private static final long serialVersionUID = -5591550896644930342L;
	
	/**
	 * 评分
	 */
	private Integer score;
	
	/**
	 * 评论内容
	 */
	private String content;
	
	/**
	 * 是否显示
	 */
	private Boolean isShow;
	
	/**
	 * 用户IP
	 */
	private String ip;
	
	/**
	 * 用户名
	 */
	private String user;
	
	/**
	 * 会员
	 */
	private Member member;
	
	/**
	 * 店铺
	 */
	private Shop shop;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * 会员
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	/**
	 * 店铺
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
}
