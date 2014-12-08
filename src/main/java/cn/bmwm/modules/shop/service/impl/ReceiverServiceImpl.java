/*


 * */
package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.dao.ReceiverDao;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Receiver;
import cn.bmwm.modules.shop.service.ReceiverService;

/**
 * Service - 收货地址
 * 
 *
 * @version 1.0
 */
@Service("receiverServiceImpl")
public class ReceiverServiceImpl extends BaseServiceImpl<Receiver, Long> implements ReceiverService {

	@Resource(name = "receiverDaoImpl")
	private ReceiverDao receiverDao;

	@Resource(name = "receiverDaoImpl")
	public void setBaseDao(ReceiverDao receiverDao) {
		super.setBaseDao(receiverDao);
	}

	public Receiver findDefault(Member member) {
		return receiverDao.findDefault(member);
	}

	public Page<Receiver> findPage(Member member, Pageable pageable) {
		return receiverDao.findPage(member, pageable);
	}
	
	/**
	 * 查询收货地址
	 * @param member
	 * @param offset
	 * @param size
	 * @return
	 */
	public List<Receiver> findList(Member member, int offset, int size) {
		return receiverDao.findList(member, offset, size);
	}
	
	/**
	 * 查询收货地址数量
	 * @param member
	 * @return
	 */
	public long count(Member member) {
		return receiverDao.count(member);
	}
	
	@Transactional
	public void saveReceiver(Receiver receiver) {
		if(receiver.getIsDefault() != null && receiver.getIsDefault() == true) {
			
		}else{
			receiverDao.persist(receiver);
		}
	}

}