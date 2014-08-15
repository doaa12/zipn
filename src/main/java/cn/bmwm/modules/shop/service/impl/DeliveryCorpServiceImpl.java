/*


 * */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.DeliveryCorpDao;
import cn.bmwm.modules.shop.entity.DeliveryCorp;
import cn.bmwm.modules.shop.service.DeliveryCorpService;

/**
 * Service - 物流公司
 * 
 *
 * @version 1.0
 */
@Service("deliveryCorpServiceImpl")
public class DeliveryCorpServiceImpl extends BaseServiceImpl<DeliveryCorp, Long> implements DeliveryCorpService {

	@Resource(name = "deliveryCorpDaoImpl")
	public void setBaseDao(DeliveryCorpDao deliveryCorpDao) {
		super.setBaseDao(deliveryCorpDao);
	}

}