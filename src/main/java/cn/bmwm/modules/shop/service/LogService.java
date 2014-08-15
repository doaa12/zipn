/*


 * */
package cn.bmwm.modules.shop.service;

import cn.bmwm.modules.shop.entity.Log;

/**
 * Service - 日志
 * 
 *
 * @version 1.0
 */
public interface LogService extends BaseService<Log, Long> {

	/**
	 * 清空日志
	 */
	void clear();

}