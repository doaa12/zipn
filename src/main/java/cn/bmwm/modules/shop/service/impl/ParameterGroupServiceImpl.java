/*


 * */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.dao.ParameterGroupDao;
import cn.bmwm.modules.shop.entity.ParameterGroup;
import cn.bmwm.modules.shop.entity.Shop;
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
	private ParameterGroupDao parameterGroupDao;

	@Resource(name = "parameterGroupDaoImpl")
	public void setBaseDao(ParameterGroupDao parameterGroupDao) {
		super.setBaseDao(parameterGroupDao);
	}
	
	/**
	 * 查找店铺参数组分页
	 * @param shop
	 * @param pageable
	 * @return
	 */
	public Page<ParameterGroup> findPage(Shop shop, Pageable pageable) {
		return parameterGroupDao.findPage(shop, pageable);
	}

}