/*


 * */
package cn.bmwm.modules.shop.service;

import cn.bmwm.modules.shop.entity.Sn.Type;

/**
 * Service - 序列号
 * 
 *
 * @version 1.0
 */
public interface SnService {

	/**
	 * 生成序列号
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	String generate(Type type);

}