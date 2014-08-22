/**
 * 
 */
package cn.bmwm.modules.shop.dao.impl;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.ShopCategoryDao;
import cn.bmwm.modules.shop.entity.ShopCategory;

/**
 * Dao - 店铺分类
 * @author zhoupuyue
 * @date 2014-8-20
 */
@Repository("shopCategoryDaoImpl")
public class ShopCategoryDaoImpl extends BaseDaoImpl<ShopCategory,Long> implements ShopCategoryDao {

}
