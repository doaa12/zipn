/*


 * */
package cn.bmwm.modules.shop.dao.impl;


import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.AdDao;
import cn.bmwm.modules.shop.entity.Ad;

/**
 * Dao - 广告
 * 
 *
 * @version 1.0
 */
@Repository("adDaoImpl")
public class AdDaoImpl extends BaseDaoImpl<Ad, Long> implements AdDao {

}