package cn.bmwm.modules.shop.controller.app.vo;

import org.apache.commons.lang.StringUtils;

/**
 * 收货地址VO
 * @author zby
 * 2014-12-7 下午9:04:16
 */
public class ReceiverVo {
	
	/**
	 * 流水号ID
	 */
	private Long id;
	
	/**
	 * 收货人
	 */
	private String consignee;
	
	/**
	 * 地区名称
	 */
	private String areaName;

	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 邮编
	 */
	private String zipCode = "";

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 是否默认
	 */
	private Boolean isDefault;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		if(StringUtils.isNotBlank(zipCode)) {
			this.zipCode = zipCode;
		}
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
}
