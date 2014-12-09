/*


 * */
package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bmwm.modules.shop.dao.AreaDao;
import cn.bmwm.modules.shop.entity.Area;
import cn.bmwm.modules.shop.service.AreaService;

/**
 * Service - 地区
 * 
 *
 * @version 1.0
 */
@Service("areaServiceImpl")
public class AreaServiceImpl extends BaseServiceImpl<Area, Long> implements AreaService {

	@Resource(name = "areaDaoImpl")
	private AreaDao areaDao;

	@Resource(name = "areaDaoImpl")
	public void setBaseDao(AreaDao areaDao) {
		super.setBaseDao(areaDao);
	}
	
	@Cacheable(value = "area", key = "'findRoots'")
	public List<Area> findRoots() {
		return areaDao.findRoots(null);
	}
	
	@Cacheable(value = "area", key = "'count' + #count + 'findRoots'")
	public List<Area> findRoots(Integer count) {
		return areaDao.findRoots(count);
	}
	
	/**
	 * 查询下一级区域
	 * @param parent
	 * @return
	 */
	@Cacheable(value = "area", key = "'parent' + #parent + 'findChildren'")
	public List<Area> findChildren(Area parent) {
		return areaDao.findChildren(parent);
	}
	
	@Override
	@Transactional
	@CacheEvict(value = "area", allEntries = true)
	public void save(Area area) {
		super.save(area);
	}

	@Override
	@Transactional
	@CacheEvict(value = "area", allEntries = true)
	public Area update(Area area) {
		return super.update(area);
	}

	@Override
	@Transactional
	@CacheEvict(value = "area", allEntries = true)
	public Area update(Area area, String... ignoreProperties) {
		return super.update(area, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "area", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "area", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "area", allEntries = true)
	public void delete(Area area) {
		super.delete(area);
	}

}