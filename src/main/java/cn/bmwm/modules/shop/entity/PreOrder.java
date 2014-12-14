package cn.bmwm.modules.shop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity -- 预约订单
 * @author zby
 * 2014-12-13 下午9:55:04
 */
@Entity
@Table(name = "xx_preorder")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_preorder_sequence")
public class PreOrder extends BaseEntity {

	private static final long serialVersionUID = -9178055836987289579L;
	
	/**
	 * 订单状态
	 */
	public enum PreOrderStatus {

		/** 未确认 */
		unconfirmed,

		/** 已确认 */
		confirmed,

		/** 已完成 */
		completed,

		/** 已取消 */
		cancelled
	}

	/**
	 * 支付状态
	 */
	public enum PaymentStatus {

		/** 未支付 */
		unpaid,

		/** 已支付 */
		paid,

		/** 已退款 */
		refunded
	}
	
	/** 订单编号 */
	private String sn;

	/** 订单状态 */
	private PreOrderStatus preOrderStatus;

	/** 支付状态 */
	private PaymentStatus paymentStatus;
	
	/**
	 * 预约时间
	 */
	private Date bookTime;
	
	/**
	 * 人数
	 */
	private Integer persons;
	
	/**
	 * 联系人姓名
	 */
	private String contactUserName;
	
	/**
	 * 联系人电话
	 */
	private String contactPhone;
	
	/**
	 * 商品总金额(不包含运费，只包含所有商品的价格)
	 */
	private BigDecimal totalAmount;
	
	/** 已付金额 */
	private BigDecimal amountPaid;
	
	/** 赠送积分 */
	private Long point;
	
	/** 附言 */
	private String memo;
	
	/** 促销 */
	private String promotion;
	
	/** 锁定到期时间 */
	private Date lockExpire;
	
	/** 到期时间 */
	private Date expire;
	
	/** 是否已分配库存 */
	private Boolean isAllocatedStock;
	
	/** 支付方式名称 */
	private String paymentMethodName;
	
	/** 会员 */
	private Member member;
	
	/**
	 * 店铺
	 */
	private Shop shop;
	
	/** 预约订单项 */
	private List<PreOrderItem> preOrderItems = new ArrayList<PreOrderItem>();

	/** 订单日志 */
	private Set<PreOrderLog> preOrderLogs = new HashSet<PreOrderLog>();
	
	/** 收款单 */
	private Set<Payment> payments = new HashSet<Payment>();
	
	/** 退款单 */
	private Set<Refunds> refunds = new HashSet<Refunds>();
	
	
	/**
	 * 获取订单编号
	 * 
	 * @return 订单编号
	 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
	/**
	 * 获取预约订单状态
	 * @return
	 */
	@Column(nullable = false)
	public PreOrderStatus getPreOrderStatus() {
		return preOrderStatus;
	}

	public void setPreOrderStatus(PreOrderStatus preOrderStatus) {
		this.preOrderStatus = preOrderStatus;
	}
	
	/**
	 * 获取预约订单支付状态
	 * @return
	 */
	@Column(nullable = false)
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	/**
	 * 获取预约时间
	 * @return
	 */
	@Column(nullable = false)
	public Date getBookTime() {
		return bookTime;
	}

	public void setBookTime(Date bookTime) {
		this.bookTime = bookTime;
	}
	
	/**
	 * 获取预约人数
	 * @return
	 */
	@Column(nullable = false)
	public Integer getPersons() {
		return persons;
	}

	public void setPersons(Integer persons) {
		this.persons = persons;
	}
	
	/**
	 * 获取预约的联系人姓名
	 * @return
	 */
	@Column(nullable = false)
	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	/**
	 * 获取联系人手机号码
	 * @return
	 */
	@Column(nullable = false)
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	/**
	 * 获取支付总金额
	 * @return
	 */
	@Column(nullable = false)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	/**
	 * 获取已经支付总金额
	 * @return
	 */
	@Column(nullable = false)
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}
	
	/**
	 * 获取送给用户的积分
	 * @return
	 */
	@Column(nullable = false)
	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}
	
	/**
	 * 获取用户给商家的留言
	 * @return
	 */
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	/**
	 * 获取促销名称
	 * @return
	 */
	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	
	/**
	 * 获取锁定到期时间
	 * @return
	 */
	public Date getLockExpire() {
		return lockExpire;
	}

	public void setLockExpire(Date lockExpire) {
		this.lockExpire = lockExpire;
	}

	/**
	 * 获取订单过期时间
	 * @return
	 */
	@Column(nullable = false)
	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	/**
	 * 获取是否分配库存
	 * @return
	 */
	@Column(nullable = false)
	public Boolean getIsAllocatedStock() {
		return isAllocatedStock;
	}

	public void setIsAllocatedStock(Boolean isAllocatedStock) {
		this.isAllocatedStock = isAllocatedStock;
	}
	
	/**
	 * 获取支付方式名称
	 * @return
	 */
	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}
	
	/**
	 * 获取用户
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	/**
	 * 获取店铺
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	/**
	 * 获取预约单项
	 * @return
	 */
	@OneToMany(mappedBy = "preOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("isGift asc")
	public List<PreOrderItem> getPreOrderItems() {
		return preOrderItems;
	}

	public void setPreOrderItems(List<PreOrderItem> preOrderItems) {
		this.preOrderItems = preOrderItems;
	}
	
	/**
	 * 获取订单日志
	 * @return
	 */
	@OneToMany(mappedBy = "preOrder", fetch = FetchType.LAZY)
	public Set<PreOrderLog> getPreOrderLogs() {
		return preOrderLogs;
	}

	public void setPreOrderLogs(Set<PreOrderLog> preOrderLogs) {
		this.preOrderLogs = preOrderLogs;
	}
	
	/**
	 * 获取支付单
	 * @return
	 */
	@OneToMany(mappedBy = "preOrder", fetch = FetchType.LAZY)
	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}
	
	/**
	 * 获取退款单
	 * @return
	 */
	@OneToMany(mappedBy = "preOrder", fetch = FetchType.LAZY)
	public Set<Refunds> getRefunds() {
		return refunds;
	}

	public void setRefunds(Set<Refunds> refunds) {
		this.refunds = refunds;
	}
	
}
