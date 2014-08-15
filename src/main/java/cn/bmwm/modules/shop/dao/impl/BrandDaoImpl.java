/*


 * */
package cn.bmwm.modules.shop.dao.impl;


import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.BrandDao;
import cn.bmwm.modules.shop.entity.Brand;

/**
 * Dao - 品牌
 * 
 *
 * @version 1.0
 */
@Repository("brandDaoImpl")
public class BrandDaoImpl extends BaseDaoImpl<Brand, Long> implements BrandDao {

}