package cn.bmwm.modules.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 预约订单日志
 * @author zby
 * 2014-12-13 下午10:33:29
 */
@Entity
@Table(name = "xx_preorder_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_preorder_log_sequence")
public class PreOrderLog extends BaseEntity {

	private static final long serialVersionUID = -4653848186412055689L;
	
	/**
	 * 类型
	 */
	public enum Type {

		/** 订单创建 */
		create,

		/** 订单修改 */
		modify,

		/** 订单确认 */
		confirm,

		/** 订单支付 */
		payment,

		/** 订单退款 */
		refunds,

		/** 订单发货 */
		shipping,

		/** 订单退货 */
		returns,

		/** 订单完成 */
		complete,

		/** 订单取消 */
		cancel,

		/** 其它 */
		other
	};

	/** 类型 */
	private Type type;

	/** 操作员 */
	private String operator;

	/** 内容 */
	private String content;

	/** 预约订单 */
	private PreOrder preOrder;
	
	
	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@Column(nullable = false, updatable = false)
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取操作员
	 * 
	 * @return 操作员
	 */
	@Column(updatable = false)
	public String getOperator() {
		return operator;
	}

	/**
	 * 设置操作员
	 * 
	 * @param operator
	 *            操作员
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@Column(updatable = false)
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取预约订单
	 * 
	 * @return 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "preOrder", nullable = false, updatable = false)
	public PreOrder getPreOrder() {
		return preOrder;
	}

	/**
	 * 设置订单
	 * 
	 * @param order
	 *            订单
	 */
	public void setPreOrder(PreOrder preOrder) {
		this.preOrder = preOrder;
	}

}
