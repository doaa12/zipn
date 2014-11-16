/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * 公共 -- Controller
 * @author zhoupuyue
 * @date 2014-10-15
 */
@Controller("appCommonController")
@RequestMapping("/app/common")
public class CommonController {
	
	public static final Log log = LogFactory.getLog(CommonController.class);
	
	/**
	 * 短信验证令牌
	 */
	private static String accessToken;
	
	/**
	 * 发送验证码短信
	 * @return
	 */
	@RequestMapping(value = "/send_code")
	@ResponseBody
	public Map<String,Object> sendValidateCode(String phone, HttpSession session) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		if(StringUtils.isBlank(phone)) {
			result.put("flag", 0);
			result.put("message", "手机号码为空！");
			return result;
		}
		
		String code = getValidateCode();
		
		Setting setting = SettingUtils.get();
		
		
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("app_id", setting.getSendSmsAppId());
		data.put("acceptor_tel", phone);
		data.put("template_param", "{\"param1\":" + code + ",\"param2\":30}");
		data.put("template_id", setting.getSendSmsTemplateId());
		data.put("timestamp", getFormatTime());
		data.put("access_token", "");
		
		session.setAttribute("code", code);
		
		result.put("version", 1);
		result.put("flag", 1);
		
		return result;
		
	}
	
	/**
	 * 刷新令牌回调接口
	 * @param code
	 * @param open_id
	 * @param res_code
	 * @param res_message
	 * @param access_token
	 * @param expires_in
	 * @param scope
	 * @return
	 */
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	@ResponseBody
	public String callback(String code, String open_id, String res_code, String res_message, String access_token, String expires_in, String scope) {
		
		log.warn("code: " + code);
		log.warn("open_id: " + open_id);
		log.warn("res_code: " + res_code);
		log.warn("res_message: " + res_message);
		log.warn("access_token: " + access_token);
		log.warn("expires_in: " + expires_in);
		log.warn("scope: " + scope);
		
		if(StringUtils.isNotBlank(access_token)) {
			accessToken = access_token;
		}
		
		return null;
		
	}
	
	/**
	 * 刷新访问Token
	 */
	public void refreshAccessToken() {
		
		Setting setting = SettingUtils.get();
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("grant_type", "refresh_token");
		data.put("refresh_token", accessToken);
		data.put("app_id", setting.getSendSmsAppId());
		data.put("app_secret", setting.getSendSmsAppSecret());
		
		
	}
	
	/**
	 * 生成短信验证码
	 * @return
	 */
	public String getValidateCode() {
		
		Random random = new Random();
		
		StringBuilder code = new StringBuilder();
		code.append(random.nextInt(10));
		code.append(random.nextInt(10));
		code.append(random.nextInt(10));
		code.append(random.nextInt(10));
		
		return code.toString();
		
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public String getFormatTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
}
