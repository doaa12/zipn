/*


 * */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.ReturnsDao;
import cn.bmwm.modules.shop.entity.Returns;
import cn.bmwm.modules.shop.service.ReturnsService;

/**
 * Service - 退货单
 * 
 *
 * @version 1.0
 */
@Service("returnsServiceImpl")
public class ReturnsServiceImpl extends BaseServiceImpl<Returns, Long> implements ReturnsService {

	@Resource(name = "returnsDaoImpl")
	public void setBaseDao(ReturnsDao returnsDao) {
		super.setBaseDao(returnsDao);
	}

}