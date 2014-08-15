/*


 * */
package cn.bmwm.modules.shop.dao.impl;


import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.PaymentMethodDao;
import cn.bmwm.modules.shop.entity.PaymentMethod;

/**
 * Dao - 支付方式
 * 
 *
 * @version 1.0
 */
@Repository("paymentMethodDaoImpl")
public class PaymentMethodDaoImpl extends BaseDaoImpl<PaymentMethod, Long> implements PaymentMethodDao {

}