/*


 * */
package cn.bmwm.modules.shop.service;

import java.util.List;

import cn.bmwm.modules.shop.entity.ProductCategory;


/**
 * Service - 商品分类
 * 
 *
 * @version 1.0
 */
public interface ProductCategoryService extends BaseService<ProductCategory, Long> {

	/**
	 * 查找顶级商品分类
	 * 
	 * @return 顶级商品分类
	 */
	List<ProductCategory> findRoots();

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<ProductCategory> findRoots(Integer count);

	/**
	 * 查找顶级商品分类(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 顶级商品分类(缓存)
	 */
	List<ProductCategory> findRoots(Integer count, String cacheRegion);

	/**
	 * 查找上级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @return 上级商品分类
	 */
	List<ProductCategory> findParents(ProductCategory productCategory);

	/**
	 * 查找上级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @return 上级商品分类
	 */
	List<ProductCategory> findParents(ProductCategory productCategory, Integer count);

	/**
	 * 查找上级商品分类(缓存)
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 上级商品分类(缓存)
	 */
	List<ProductCategory> findParents(ProductCategory productCategory, Integer count, String cacheRegion);

	/**
	 * 查找商品分类树
	 * 
	 * @return 商品分类树
	 */
	List<ProductCategory> findTree();
	
	/**
	 * 获取商品分层分类树
	 * @return
	 */
	List<ProductCategory> findHierarchicalTree();

	/**
	 * 查找下级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @return 下级商品分类
	 */
	List<ProductCategory> findChildren(ProductCategory productCategory);

	/**
	 * 查找下级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<ProductCategory> findChildren(ProductCategory productCategory, Integer count);

	/**
	 * 查找下级商品分类(缓存)
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 下级商品分类(缓存)
	 */
	List<ProductCategory> findChildren(ProductCategory productCategory, Integer count, String cacheRegion);

}