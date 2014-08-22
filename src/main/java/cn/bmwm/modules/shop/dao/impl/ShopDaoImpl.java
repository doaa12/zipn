/**
 * 
 */
package cn.bmwm.modules.shop.dao.impl;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.ShopDao;
import cn.bmwm.modules.shop.entity.Shop;

/**
 * @author zhoupuyue
 * @date 2014-8-22
 */
@Repository("shopDaoImpl")
public class ShopDaoImpl extends BaseDaoImpl<Shop,Long> implements ShopDao {

}
