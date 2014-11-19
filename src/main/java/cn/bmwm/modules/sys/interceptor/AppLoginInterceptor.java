/**
 * 
 */
package cn.bmwm.modules.sys.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.bmwm.common.Constants;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.utils.SettingUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Interceptor -- App登录检查
 * @author zhoupuyue
 * @date 2014-10-17
 */
public class AppLoginInterceptor extends HandlerInterceptorAdapter {
	
	public static final Log log = LogFactory.getLog(AppLoginInterceptor.class);

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String principal = request.getHeader(Constants.USER_LOGIN_MARK);
		
		Setting setting = SettingUtils.get();
		int keepLoginDays = setting.getKeepLoginDays();
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		if(StringUtils.isBlank(principal)) {
			result.put("flag", 401);
			write(response, result);
			return false;
		}
		
		if(principal.indexOf("@") == -1) {
			result.put("flag", 401);
			write(response, result);
			return false;
		}
		
		HttpSession session = request.getSession();
		Object sessionPrincipal = session.getAttribute(Constants.USER_LOGIN_MARK);
		
		if(sessionPrincipal == null) {
			
			String validation = principal.substring(0, principal.lastIndexOf("@"));
			String sid = principal.substring(principal.lastIndexOf("@") + 1);
			
			long id = 0;
			
			try{
				id = Long.parseLong(sid);
			}catch(NumberFormatException e) {
				result.put("flag", 401);
				write(response, result);
				return false;
			}
			
			Member member = memberService.find(id);
			
			if(member == null) {
				result.put("flag", 401);
				write(response, result);
				return false;
			}
			
			if(!DigestUtils.md5Hex(sid + member.getPassword()).equals(validation)) {
				result.put("flag", 401);
				write(response, result);
				return false;
			}
			
			Date loginDate = member.getLoginDate();
			
			long untilTime = loginDate.getTime() + keepLoginDays * 24 * 60 * 60 * 1000L;
			
			if(System.currentTimeMillis() > untilTime) {
				result.put("flag", 401);
				write(response, result);
				return false;
			}
			
			session.setAttribute(Constants.USER_LOGIN_MARK, member);
			
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
