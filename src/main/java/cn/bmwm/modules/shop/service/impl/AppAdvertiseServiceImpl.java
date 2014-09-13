package cn.bmwm.modules.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bmwm.modules.shop.dao.AppAdvertiseDao;
import cn.bmwm.modules.shop.entity.AppAdvertise;
import cn.bmwm.modules.shop.service.AppAdvertiseService;

/**
 * Service -- App广告
 * @author zby
 * 2014-9-13 上午9:22:45
 */
@Service("appAdvertiseServiceImpl")
public class AppAdvertiseServiceImpl extends BaseServiceImpl<AppAdvertise,Long> implements AppAdvertiseService {
	
	@Resource(name = "appAdvertiseDaoImpl")
	private AppAdvertiseDao appAdvertiseDao;
	
	@Resource(name = "appAdvertiseDaoImpl")
	public void setBaseDao(AppAdvertiseDao appAdvertiseDao) {
		super.setBaseDao(appAdvertiseDao);
	}
	
	/**
	 * 获取城市广告
	 * @param city
	 * @return
	 */
	@Cacheable(value = "appAd", key = "'city' + #city + 'findByCity'")
	public List<AppAdvertise> findByCity(String city) {
		return appAdvertiseDao.findByCity(city);
	}
	
	@Override
	@Transactional
	@CacheEvict(value = {"appAd", "area"}, allEntries = true)
	public void save(AppAdvertise appAdvertise) {
		super.save(appAdvertise);
	}
	
	@Override
	@Transactional
	@CacheEvict(value = {"appAd", "area"}, allEntries = true)
	public AppAdvertise update(AppAdvertise appAdvertise) {
		return super.update(appAdvertise);
	}
	
}
