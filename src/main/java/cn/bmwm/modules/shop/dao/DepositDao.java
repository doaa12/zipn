/*


 * */
package cn.bmwm.modules.shop.dao;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.Deposit;
import cn.bmwm.modules.shop.entity.Member;

/**
 * Dao - 预存款
 * 
 *
 * @version 1.0
 */
public interface DepositDao extends BaseDao<Deposit, Long> {

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