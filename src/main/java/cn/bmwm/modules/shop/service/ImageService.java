/*


 * */
package cn.bmwm.modules.shop.service;

import cn.bmwm.modules.shop.entity.ProductImage;
import cn.bmwm.modules.shop.entity.ShopImage;

/**
 * Service - 图片
 * 
 *
 * @version 1.0
 */
public interface ImageService {

	/**
	 * 生成商品图片
	 * 
	 * @param productImage
	 *            商品图片
	 */
	void build(ProductImage productImage);
	
	/**
	 * 店铺图片
	 * @param shopImage
	 */
	void build(ShopImage shopImage);

}