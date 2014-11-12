/**
 * 
 */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.ProductSpecificationDao;
import cn.bmwm.modules.shop.entity.ProductSpecification;
import cn.bmwm.modules.shop.service.ProductSpecificationService;

/**
 * 商品规格 -- Service
 * @author zhoupuyue
 * @date 2014-11-12
 */
@Service("productSpecificationServiceImpl")
public class ProductSpecificationServiceImpl extends BaseServiceImpl<ProductSpecification,Long> implements ProductSpecificationService {

	@Resource(name = "productSpecificationDaoImpl")
	public void setBaseDao(ProductSpecificationDao productSpecificationDao) {
		super.setBaseDao(productSpecificationDao);
	}
	
}
