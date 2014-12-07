/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Constants;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Member.Gender;
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.shop.service.RSAService;

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
	@RequestMapping(value = "/change_pasword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> changePassword(HttpSession session, String oldpassword, String newpassword) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		if(StringUtils.isBlank(oldpassword)) {
			result.put("message", "旧密码为空！");
			result.put("flag", Constants.USER_PASSWORD_BLANK);
			return result;
		}
		
		if(StringUtils.isBlank(newpassword)) {
			result.put("message", "新密码为空！");
			result.put("flag", Constants.USER_PASSWORD_BLANK);
			return result;
		}
		
		Member member = (Member)session.getAttribute(Constants.USER_LOGIN_MARK);
		
		String newPassword = rsaService.decrypt(newpassword);
		String oldPassword = rsaService.decrypt(oldpassword);
		
		if (!DigestUtils.md5Hex(oldPassword).equals(member.getPassword())) {
			result.put("message", "旧密码错误！");
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
	@RequestMapping(value = "/get_userinfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getUserInfo(HttpSession session) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		Member member = (Member)session.getAttribute(Constants.USER_LOGIN_MARK);
		
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
	@RequestMapping(value = "/modify_userinfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> modifyUserInfo(HttpSession session, String address, String description, Integer sex) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		Member member = (Member)session.getAttribute(Constants.USER_LOGIN_MARK);
		
		if(sex != null) {
			member.setGender(sex == 1 ? Gender.male : Gender.female);
		}
		
		member.setAddress(address);
		member.setDescription(description);
		memberService.update(member);
		
		result.put("flag", 1);
		
		return result;
		
	}
	
	/**
	 * 重置密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reset_password", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resetPassword(HttpSession session, String phone, String code, String enpassword) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		if(StringUtils.isBlank(code)) {
			result.put("message", "验证码为空！");
			result.put("flag", Constants.USER_CODE_EMPTY);
			return result;
		}
		
		if(StringUtils.isBlank(phone)) {
			result.put("message", "手机号码为空！");
			result.put("flag", Constants.USER_USERNAME_BLANK);
			return result;
		}
		
		if(StringUtils.isBlank(enpassword)) {
			result.put("message", "密码为空！");
			result.put("flag", Constants.USER_PASSWORD_BLANK);
			return result;
		}
		
		Object ocode = session.getAttribute(Constants.VALIDATION_CODE);
		
		if(ocode == null) {
			result.put("message", "验证码错误！");
			result.put("flag", Constants.USER_CODE_ERROR);
			return result;
		}
		
		String scode = (String)ocode;
		
		if(!code.equals(scode)) {
			result.put("message", "验证码错误！");
			result.put("flag", Constants.USER_CODE_ERROR);
			return result;
		}
		
		String password = rsaService.decrypt(enpassword);
		
		Member member = memberService.findByUsername(phone);
		
		if(member == null) {
			result.put("message", "手机号码未注册！");
			result.put("flag", Constants.USER_USER_NOT_EXISTS);
			return result;
		}
		
		member.setPassword(DigestUtils.md5Hex(password));
		memberService.update(member);
		
		result.put("flag", 1);
		
		return result;
		
	}
	
}
