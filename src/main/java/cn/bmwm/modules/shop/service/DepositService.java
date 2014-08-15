/*


 * */
package cn.bmwm.modules.shop.service;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.Deposit;
import cn.bmwm.modules.shop.entity.Member;

/**
 * Service - 预存款
 * 
 *
 * @version 1.0
 */
public interface DepositService extends BaseService<Deposit, Long> {

	/**
	 * 查找预存款分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 预存款分页
	 */
	Page<Deposit> findPage(Member member, Pageable pageable);

}