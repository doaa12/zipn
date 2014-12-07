/*


 * */
package cn.bmwm.modules.shop.dao;

import java.util.List;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Receiver;

/**
 * Dao - 收货地址
 * 
 *
 * @version 1.0
 */
public interface ReceiverDao extends BaseDao<Receiver, Long> {

	/**
	 * 查找默认收货地址
	 * 
	 * @param member
	 *            会员
	 * @return 默认收货地址，若不存在则返回最新收货地址
	 */
	Receiver findDefault(Member member);

	/**
	 * 查找收货地址分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 收货地址分页
	 */
	Page<Receiver> findPage(Member member, Pageable pageable);
	
	/**
	 * 查询收货地址
	 * @param member
	 * @param offset
	 * @param size
	 * @return
	 */
	List<Receiver> findList(Member member, int offset, int size);

}