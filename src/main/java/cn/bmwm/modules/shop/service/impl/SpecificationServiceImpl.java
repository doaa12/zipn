/*


 * */
package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.SpecificationDao;
import cn.bmwm.modules.shop.entity.Shop;
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
	private SpecificationDao specificationDao;
	
	@Resource(name = "specificationDaoImpl")
	public void setBaseDao(SpecificationDao specificationDao) {
		super.setBaseDao(specificationDao);
	}
	
	/**
	 * 查询店铺商品规格
	 * @param shop
	 * @return
	 */
	public List<Specification> findShopSpecificationList(Shop shop) {
		return specificationDao.findShopSpecificationList(shop);
	}

}