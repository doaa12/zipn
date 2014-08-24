/*


 * */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.SpecificationDao;
import cn.bmwm.modules.shop.entity.Specification;
import cn.bmwm.modules.shop.service.SpecificationService;

/**
 * Service - 规格
 * 
 *
 * @version 1.0
 */
@Service("specificationServiceImpl")
public class SpecificationServiceImpl extends BaseServiceImpl<Specification, Long> implements SpecificationService {

	@Resource(name = "specificationDaoImpl")
	public void setBaseDao(SpecificationDao specificationDao) {
		super.setBaseDao(specificationDao);
	}

}