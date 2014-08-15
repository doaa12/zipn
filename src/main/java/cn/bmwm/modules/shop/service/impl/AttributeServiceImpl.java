/*


 * */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.AttributeDao;
import cn.bmwm.modules.shop.entity.Attribute;
import cn.bmwm.modules.shop.service.AttributeService;

/**
 * Service - 属性
 * 
 *
 * @version 1.0
 */
@Service("attributeServiceImpl")
public class AttributeServiceImpl extends BaseServiceImpl<Attribute, Long> implements AttributeService {

	@Resource(name = "attributeDaoImpl")
	public void setBaseDao(AttributeDao attributeDao) {
		super.setBaseDao(attributeDao);
	}

}