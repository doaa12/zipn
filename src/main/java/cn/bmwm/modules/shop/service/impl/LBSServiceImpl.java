package cn.bmwm.modules.shop.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.service.HttpService;
import cn.bmwm.modules.shop.service.LBSService;
import cn.bmwm.modules.sys.exception.BusinessException;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.utils.SettingUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Service -- 定位服务
 * @author zby
 * 2014-9-8 下午10:23:07
 */
@Service("lbsServiceImpl")
public class LBSServiceImpl implements LBSService {

	@Resource(name = "httpServiceImpl")
	private HttpService httpService;
	
	/**
	 * 根据地址获取经纬度，元素0是纬度，元素1是经度
	 * @return
	 * @param address
	 */
	@Override
	public BigDecimal[] getCoordinate(String address) {
		
		if(address == null || address.trim().equals("")) {
			throw new BusinessException("请输入店铺地址！");
		}
		
		Setting setting = SettingUtils.get();
		
		String ak = setting.getLbsAk();
		String url = setting.getLbsURL();
		
		url = url.replace("{ak}", ak);
		url = url.replace("{address}", address);
		
		JSONObject result = httpService.executeGet(url);
		
		Integer status = result.getInteger("status");
		
		if(status == null || status != 0) {
			throw new BusinessException("无法定位店铺地址，请检查地址是否正确或者提供更精确的地址！");
		}
		
		JSONObject data = result.getJSONObject("result");
		
		if(data == null) {
			throw new BusinessException("无法定位店铺地址，请检查地址是否正确或者提供更精确的地址！");
		}
		
		JSONObject location = data.getJSONObject("location");
		
		if(location == null) {
			throw new BusinessException("无法定位店铺地址，请检查地址是否正确或者提供更精确的地址！");
		}
		
		BigDecimal x = location.getBigDecimal("lng");
		BigDecimal y = location.getBigDecimal("lat");
		
		if(x == null || y == null) {
			throw new BusinessException("无法定位店铺地址，请检查地址是否正确或者提供更精确的地址！");
		}
		
		return new BigDecimal[]{y, x};
		
	}

}
