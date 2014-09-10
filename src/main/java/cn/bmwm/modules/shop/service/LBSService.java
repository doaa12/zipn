package cn.bmwm.modules.shop.service;

import java.math.BigDecimal;

/**
 * Service -- 定位服务
 * @author zby
 * 2014-9-8 下午10:16:19
 */
public interface LBSService {

	/**
	 * 根据地址获取经纬度
	 * @return
	 * @param address
	 */
	public BigDecimal[] getCoordinate(String address);
	
	/**
	 * 计算两地距离
	 * @param lat1：纬度
	 * @param lng1：经度
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public double getDistance(double lat1, double lng1, double lat2, double lng2);
	
}
