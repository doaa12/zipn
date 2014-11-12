/**
 * 
 */
package cn.bmwm.modules.shop.dao.impl;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.ProductSpecificationDao;
import cn.bmwm.modules.shop.entity.ProductSpecification;

/**
 * 商品规格 -- Dao
 * @author zhoupuyue
 * @date 2014-11-12
 */
@Repository("productSpecificationDaoImpl")
public class ProductSpecificationDaoImpl extends BaseDaoImpl<ProductSpecification,Long> implements ProductSpecificationDao {

}
