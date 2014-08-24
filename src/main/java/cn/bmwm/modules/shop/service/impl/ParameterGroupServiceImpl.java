/*


 * */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.ParameterGroupDao;
import cn.bmwm.modules.shop.entity.ParameterGroup;
import cn.bmwm.modules.shop.service.ParameterGroupService;

/**
 * Service - 参数组
 * 
 *
 * @version 1.0
 */
@Service("parameterGroupServiceImpl")
public class ParameterGroupServiceImpl extends BaseServiceImpl<ParameterGroup, Long> implements ParameterGroupService {

	@Resource(name = "parameterGroupDaoImpl")
	public void setBaseDao(ParameterGroupDao parameterGroupDao) {
		super.setBaseDao(parameterGroupDao);
	}

}