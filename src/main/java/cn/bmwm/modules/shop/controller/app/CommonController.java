/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公共 -- Controller
 * @author zhoupuyue
 * @date 2014-10-15
 */
@Controller("appCommonController")
@RequestMapping("/app/common")
public class CommonController {
	
	
	/**
	 * 发送验证码短信
	 * @return
	 */
	@RequestMapping(value = "/send_code")
	public Map<String,Object> sendValidateCode(HttpSession session) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		String code = getValidateCode();
		
		session.setAttribute("code", code);
		result.put("code", code);
		result.put("flag", 1);
		
		return result;
		
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
	
}
