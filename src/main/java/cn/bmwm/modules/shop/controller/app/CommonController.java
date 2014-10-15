/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.modules.shop.service.RSAService;

/**
 * 公共 -- Controller
 * @author zhoupuyue
 * @date 2014-10-15
 */
@Controller("appCommonController")
@RequestMapping("/app/common")
public class CommonController {

	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	
	/**
	 * 公钥
	 */
	@RequestMapping(value = "/public_key", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> publicKey(HttpServletRequest request) {
		RSAPublicKey publicKey = rsaService.generateKey(request);
		Map<String, String> data = new HashMap<String, String>();
		data.put("n", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
		data.put("e", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
		return data;
	}
	
}
