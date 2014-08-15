/*


 * */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.SpecificationValueDao;
import cn.bmwm.modules.shop.entity.SpecificationValue;
import cn.bmwm.modules.shop.service.SpecificationValueService;

/**
 * Service - 规格值
 * 
 *
 * @version 1.0
 */
@Service("specificationValueServiceImpl")
public class SpecificationValueServiceImpl extends BaseServiceImpl<SpecificationValue, Long> implements SpecificationValueService {

	@Resource(name = "specificationValueDaoImpl")
	public void setBaseDao(SpecificationValueDao specificationValueDao) {
		super.setBaseDao(specificationValueDao);
	}

}