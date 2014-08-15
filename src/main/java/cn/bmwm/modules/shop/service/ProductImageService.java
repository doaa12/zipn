/*


 * */
package cn.bmwm.modules.shop.service;

import cn.bmwm.modules.shop.entity.ProductImage;

/**
 * Service - 商品图片
 * 
 *
 * @version 1.0
 */
public interface ProductImageService {

	/**
	 * 生成商品图片
	 * 
	 * @param productImage
	 *            商品图片
	 */
	void build(ProductImage productImage);

}