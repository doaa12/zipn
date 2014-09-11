/**
 * 
 */
package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.VirtualShopCategoryDao;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.entity.VirtualShopCategory;
import cn.bmwm.modules.shop.service.VirtualShopCategoryService;

/**
 * Service -- 虚拟店铺分类
 * @author zhoupuyue
 * @date 2014-9-11
 */
@Service("virtualShopCategoryServiceImpl")
public class VirtualShopCategoryServiceImpl extends BaseServiceImpl<VirtualShopCategory,Long> implements VirtualShopCategoryService {

	@Resource(name = "virtualShopCategoryDaoImpl")
	private VirtualShopCategoryDao virtualShopCategoryDao;
	
	@Resource(name = "virtualShopCategoryDaoImpl")
	public void setBaseDao(VirtualShopCategoryDao virtualShopCategoryDao) {
		super.setBaseDao(virtualShopCategoryDao);
	}
	
	/**
	 * 查询虚拟店铺分类
	 * @param city
	 * @return
	 */
	public List<VirtualShopCategory> findList(String city) {
		return virtualShopCategoryDao.findList(city);
	}
	
	/**
	 * 查询虚拟分类店铺
	 * @param category
	 * @return
	 */
	public List<Shop> findShopList(Long catId) {
		return virtualShopCategoryDao.findShopList(catId);
	}
	
}
