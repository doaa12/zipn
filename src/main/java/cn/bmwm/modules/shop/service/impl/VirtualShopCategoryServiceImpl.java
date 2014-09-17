/**
 * 
 */
package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bmwm.modules.shop.dao.VirtualShopCategoryDao;
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
	@Cacheable(value = "virtualShopCategory", key = "'city' + #city + 'findList'")
	public List<VirtualShopCategory> findList(String city) {
		return virtualShopCategoryDao.findList(city);
	}
	
	/**
	 * 后台管理查询虚拟店铺分类
	 * @param city
	 * @return
	 */
	public List<VirtualShopCategory> adminFindList(String city) {
		return virtualShopCategoryDao.findList(city);
	}
	
	@Override
	@Transactional
	@CacheEvict(value = {"virtualShopCategory", "shop"}, allEntries = true)
	public void save(VirtualShopCategory virtualShopCategory) {
		super.save(virtualShopCategory);
	}
	
	@Override
	@Transactional
	@CacheEvict(value = {"virtualShopCategory", "shop"}, allEntries = true)
	public VirtualShopCategory update(VirtualShopCategory virtualShopCategory) {
		return super.update(virtualShopCategory);
	}
	
	@Override
	@Transactional
	@CacheEvict(value = {"virtualShopCategory", "shop"}, allEntries = true)
	public void delete(Long ... ids) {
		super.delete(ids);
	}
	
}
