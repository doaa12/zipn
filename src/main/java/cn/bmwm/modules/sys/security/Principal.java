package cn.bmwm.modules.sys.security;

import java.io.Serializable;

/**
 * 身份信息
 * 
 * @version 1.0
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 5798882004228239559L;

	/** ID */
	private Long id;

	/** 用户名 */
	private String username;
	
	/**
	 * 商家店铺ID
	 */
	private Long shopId;
	
	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 */
	public Principal(Long id, String username, Long shopId) {
		this.id = id;
		this.username = username;
		this.shopId = shopId;
	}
	
	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 */
	public Principal(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return username;
	}

	/**
	 * @return the shopId
	 */
	public Long getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

}