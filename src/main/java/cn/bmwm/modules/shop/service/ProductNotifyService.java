/*


 * */
package cn.bmwm.modules.shop.service;

import cn.bmwm.common.persistence.Page;
import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Product;
import cn.bmwm.modules.shop.entity.ProductNotify;

/**
 * Service - 到货通知
 * 
 *
 * @version 1.0
 */
public interface ProductNotifyService extends BaseService<ProductNotify, Long> {

	/**
	 * 判断到货通知是否存在
	 * 
	 * @param product
	 *            商品
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 到货通知是否存在
	 */
	boolean exists(Product product, String email);

	/**
	 * 查找到货通知分页
	 * 
	 * @param member
	 *            会员
	 * @param isMarketable
	 *            是否上架
	 * @param isOutOfStock
	 *            商品是否缺货
	 * @param hasSent
	 *            是否已发送.
	 * @param pageable
	 *            分页信息
	 * @return 到货通知分页
	 */
	Page<ProductNotify> findPage(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent, Pageable pageable);

	/**
	 * 查找到货通知数量
	 * 
	 * @param member
	 *            会员
	 * @param isMarketable
	 *            是否上架
	 * @param isOutOfStock
	 *            商品是否缺货
	 * @param hasSent
	 *            是否已发送.
	 * @return 到货通知数量
	 */
	Long count(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent);

	/**
	 * 发送到货通知
	 * 
	 * @param ids
	 *            ID
	 * @return 发送到货通知数
	 */
	int send(Long[] ids);

}