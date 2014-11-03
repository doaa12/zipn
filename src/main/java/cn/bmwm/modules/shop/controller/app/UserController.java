/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.utils.Constants;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Member.Gender;
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.shop.service.RSAService;
import cn.bmwm.modules.sys.exception.IllegalUserStatusException;

/**
 * App - 用户信息
 * @author zhoupuyue
 * @date 2014-11-3
 */
@Controller
@RequestMapping(value = "/app/user")
public class UserController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	
	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping(value = "/change_password", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> changePassword(HttpServletRequest request, HttpServletResponse response) {
		
		String newenPassword = request.getParameter("newpassword");
		String oldenPassword = request.getParameter("oldpassword");
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		String principal = request.getHeader("principle");
		if(principal.indexOf("@") < 0) {
			throw new IllegalUserStatusException();
		}
		
		String id = principal.substring(principal.lastIndexOf("@") + 1);
		
		if(StringUtils.isBlank(id) || !StringUtils.isNumeric(id)) throw new IllegalUserStatusException();
		
		Member member = memberService.find(Long.parseLong(id));
				
		String newPassword = rsaService.decrypt(newenPassword);
		String oldPassword = rsaService.decrypt(oldenPassword);
		
		if(StringUtils.isBlank(newenPassword)) {
			result.put("flag", Constants.USER_PASSWORD_BLANK);
			return result;
		}
		
		if (!DigestUtils.md5Hex(oldPassword).equals(member.getPassword())) {
			result.put("flag", Constants.USER_PASSWORD_ERROR);
			return result;
		}
		
		member.setPassword(DigestUtils.md5Hex(newPassword));
		memberService.update(member);
		
		result.put("flag", 1);
		
		return result;
		
	}
	
	/**
	 * 获取用户信息
	 * @return
	 */
	@RequestMapping(value = "/userinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUserInfo(HttpServletRequest request) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		String principal = request.getHeader("principle");
		if(principal.indexOf("@") < 0) {
			throw new IllegalUserStatusException();
		}
		
		String id = principal.substring(principal.lastIndexOf("@") + 1);
		
		if(StringUtils.isBlank(id) || !StringUtils.isNumeric(id)) throw new IllegalUserStatusException();
		
		Member member = memberService.find(Long.parseLong(id));
		
		result.put("address", member.getAddress());
		result.put("username", member.getUsername());
		result.put("sex", member.getGender() == Gender.male ? 1 : 0);
		result.put("description", member.getDescription());
		
		return result;
		
	}
	
	/**
	 * 修改用户信息
	 * @param address
	 * @param sex
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> modify(String address, Integer sex, String description, HttpServletRequest request) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		String principal = request.getHeader("principle");
		if(principal.indexOf("@") < 0) {
			throw new IllegalUserStatusException();
		}
		
		String id = principal.substring(principal.lastIndexOf("@") + 1);
		
		if(StringUtils.isBlank(id) || !StringUtils.isNumeric(id)) throw new IllegalUserStatusException();
		
		Member member = memberService.find(Long.parseLong(id));
		
		member.setAddress(address);
		member.setGender(sex == 1 ? Gender.male : Gender.female);
		member.setDescription(description);
		memberService.update(member);
		
		result.put("flag", 1);
		
		return result;
		
	}
	

}
