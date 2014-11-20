/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Constants;
import cn.bmwm.common.utils.HttpClientUtils;
import cn.bmwm.modules.shop.entity.SmsAccessToken;
import cn.bmwm.modules.shop.service.RSAService;
import cn.bmwm.modules.shop.service.SmsAccessTokenService;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.utils.SettingUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 公共 -- Controller
 * @author zhoupuyue
 * @date 2014-10-15
 */
@Controller("appCommonController")
@RequestMapping("/app/common")
public class CommonController {
	
	public static final Log log = LogFactory.getLog(CommonController.class);
	
	@Resource(name = "smsAccessTokenServiceImpl")
	private SmsAccessTokenService smsAccessTokenService;
	
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	
	/**
	 * 手机号码为空
	 */
	public static final int SMS_PHONE_EMPTY = 101;
	
	/**
	 * Token失效
	 */
	public static final int SMS_TOKEN_INVALID = 102;
	
	/**
	 * 发送短信失败
	 */
	public static final int SMS_SEND_ERROR = 103;
	
	/**
	 * 发送短信次数超过上限
	 */
	public static final int SMS_SEND_TIMES = 104;
	
	/**
	 * 非法的请求
	 */
	public static final int SMS_INVALID_REQUEST = 105;
	
	/**
	 * 短信验证码Token
	 */
	private SmsAccessToken token;
	
	/**
	 * 每个人发送短信阀值
	 */
	private int threshold = 10;
	
	/**
	 * 发送短信历史记录
	 */
	private Map<String,Integer> history = new ConcurrentHashMap<String,Integer>();
	
	/**
	 * 发送验证码短信
	 * @return
	 */
	@RequestMapping(value = "/send_code", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> sendValidationCode(HttpServletRequest request, HttpSession session, String enphone) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		if(StringUtils.isBlank(enphone)) {
			result.put("flag", SMS_PHONE_EMPTY);
			result.put("message", "手机号码为空！");
			return result;
		}
		
		String phone = "";
		
		try{
			phone = rsaService.decrypt(enphone);
		}catch(Exception e) {
			log.error("解码异常！enphone=" + enphone, e);
			result.put("flag", SMS_INVALID_REQUEST);
			result.put("message", "非法的请求！");
			return result;
		}
		
		if(StringUtils.isBlank(phone)) {
			result.put("flag", SMS_PHONE_EMPTY);
			result.put("message", "手机号码为空！");
			return result;
		}
		
		String key = phone + "_" + getFormatDate();
		
		Integer times = history.get(key);
		
		if(times != null && times >= threshold) {
			result.put("flag", SMS_SEND_TIMES);
			result.put("message", "今日发送短信次数超过上限！");
			return result;
		}
		
		if(token == null) {
			synchronized(this) {
				if(token == null) {
					token = getSmsAccessToken();
				}
			}
		}
		
		if(token == null) {
			result.put("flag", SMS_TOKEN_INVALID);
			result.put("message", "Token失效！");
			return result;
		}
		
		if(token.getExpireDate().before(new Date())) {
			synchronized(this) {
				if(token.getExpireDate().before(new Date())) {
					refreshAccessToken(token.getAccessToken());
				}
			}
		}
		
		if(token == null) {
			result.put("flag", SMS_TOKEN_INVALID);
			result.put("message", "Token失效！");
			return result;
		}
		
		String code = getValidationCode();
		
		Setting setting = SettingUtils.get();
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("app_id", setting.getSendSmsAppId());
		data.put("access_token", token.getAccessToken());
		data.put("acceptor_tel", phone);
		data.put("template_id", setting.getSendSmsTemplateId());
		data.put("template_param", "{\"param1\":" + code + ",\"param2\":30}");
		data.put("timestamp", getFormatTime());
		
		JSONObject object = null;
		String text = "";
		
		try{
			
			text = HttpClientUtils.httpPost(setting.getSendSmsUrl(), data, "GBK");
			object = JSONObject.parseObject(text);
			
		}catch(JSONException e) {
			log.error("解析JSON异常！text=" + text, e);
			history.put(key, times == null ? 1 : times + 1);
			session.setAttribute(Constants.VALIDATION_CODE, code);
			result.put("version", 1);
			result.put("flag", 1);
			return result;
		}
		
		if(object == null) {
			result.put("flag", SMS_SEND_ERROR);
			result.put("message", "发送短信失败！");
			return result;
		}
		
		Integer res_code = object.getInteger("res_code");
		
		if(res_code == null || res_code != 0) {
			result.put("flag", SMS_SEND_ERROR);
			result.put("message", "发送短信失败！");
			return result;
		}
		
		history.put(key, times == null ? 1 : times + 1);
		
		session.setAttribute(Constants.VALIDATION_CODE, code);
		
		result.put("version", 1);
		result.put("flag", 1);
		
		return result;
		
	}
	
	/**
	 * 刷新访问Token
	 */
	public void refreshAccessToken(String refreshToken) {
		
		Setting setting = SettingUtils.get();
		
		/*
		StringBuilder url = new StringBuilder();
		url.append("https://oauth.api.189.cn/emp/oauth2/v3/access_token");
		url.append("?grant_type=refresh_token&refresh_token=").append(refreshToken).append("&app_id=").append(setting.getSendSmsAppId());
		url.append("&app_secret=").append(setting.getSendSmsAppSecret());
		*/
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("grant_type", "refresh_token");
		data.put("refresh_token", refreshToken);
		data.put("app_id", setting.getSendSmsAppId());
		data.put("app_secret", setting.getSendSmsAppSecret());
		
		String text = HttpClientUtils.httpPost("https://oauth.api.189.cn/emp/oauth2/v3/access_token", data, "GBK");
		
		JSONObject result = JSONObject.parseObject(text);
		
		if(result == null) {
			log.warn("刷新Access Token失败！result = null");
			return;
		}
		
		int rescode = result.getInteger("res_code");
		
		if(rescode != 0) {
			log.warn("刷新Access Token失败！res_code = " + rescode + ", res_message = " + result.getString("res_message"));
			return;
		}
		
		String accessToken = result.getString("access_token");
		
		int expires_in = result.getInteger("expires_in");
		Date expireTime = new Date(System.currentTimeMillis() + expires_in * 1000);
		
		if(token != null) {
			token.setAccessToken(accessToken);
			token.setExpireDate(expireTime);
			this.smsAccessTokenService.update(token);
		}
		
	}
	
	/**
	 * 从数据库查询Access Token
	 * @return
	 */
	public SmsAccessToken getSmsAccessToken() {
		List<SmsAccessToken> tokenList = this.smsAccessTokenService.findAll();
		if(tokenList == null || tokenList.size() == 0) return null;
		return tokenList.get(0);
	}
	
	/**
	 * 生成短信验证码
	 * @return
	 */
	public static String getValidationCode() {
		
		Random random = new Random();
		
		StringBuilder code = new StringBuilder();
		code.append(random.nextInt(9) + 1);
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
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public String getFormatDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	
	public static void main(String[] args) {
		
		for(int i = 0 ; i < 100 ; i++ ) {
			System.out.println(getValidationCode());
		}
		
	}
	
}
