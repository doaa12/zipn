package cn.bmwm.modules.shop.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import cn.bmwm.modules.shop.service.HttpService;
import cn.bmwm.modules.shop.service.LBSService;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * Service -- 定位服务
 * @author zby
 * 2014-9-8 下午10:23:07
 */
public class LBSServiceImpl implements LBSService {

	@Resource(name = "httpServiceImpl")
	private HttpService httpService;
	
	/**
	 * 根据地址获取经纬度，元素0是纬度，元素1是经度
	 * @return
	 * @param address
	 */
	@Override
	public double[] getCoordinate(String address) {
		
		Setting setting = SettingUtils.get();
		
		String ak = setting.getLbsAk();
		String url = setting.getLbsURL();
		
		url = url.replace("{ak}", ak);
		url = url.replace("{address}", address);
		Map<String,Object> result = httpService.executeGet(url);
		return null;
	}

}
