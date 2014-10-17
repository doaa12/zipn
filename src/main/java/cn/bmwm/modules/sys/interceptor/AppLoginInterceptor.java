/**
 * 
 */
package cn.bmwm.modules.sys.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;

import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * Interceptor -- App登录检查
 * @author zhoupuyue
 * @date 2014-10-17
 */
public class AppLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String principle = request.getHeader("principle");
		String lastLoginTime = request.getHeader("lastLoginTime");
		
		Setting setting = SettingUtils.get();
		int keepLoginDays = setting.getKeepLoginDays();
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		if(StringUtils.isBlank(principle) || StringUtils.isBlank(lastLoginTime)) {
			result.put("flag", 401);
			write(response, result);
			return false;
		}
		
		long time = Long.parseLong(lastLoginTime);
		long untilTime = time + keepLoginDays * 24 * 60 * 60 *1000L;
		
		if(System.currentTimeMillis() > untilTime) {
			result.put("flag", 401);
			write(response, result);
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * 输出到客户端
	 */
	public void write(HttpServletResponse response, Object content) {
		
		PrintWriter writer = null;
		
		try {
			
			writer = response.getWriter();
			String json = JSONObject.toJSONString(content);
			writer.print(json);
			writer.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null) {
				writer.close();
			}
		}
	}
	
}
