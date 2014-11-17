/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class UserController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	
	@RequestMapping(value = "/app/user", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> execute(String apiName, HttpServletRequest request, HttpServletResponse response) {
		
		if("changePasword".equals(apiName)) {
			return changePassword(request, response);
		} else if("getUserInfo".equals(apiName)) {
			return getUserInfo(request);
		} else if("modifyUserInfo".equals(apiName)) {
			return modifyUserInfo(request);
		} else if("resetPassword".equals(apiName)) {
			return resetPassword(request);
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("flag", 404);
		result.put("version", 1);
		
		return result;
		
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	public Map<String,Object> changePassword(HttpServletRequest request, HttpServletResponse response) {
		
		String oldenPassword = request.getParameter("oldpassword");
		String newenPassword = request.getParameter("newpassword");
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		if(StringUtils.isBlank(oldenPassword)) {
			result.put("message", "旧密码为空！");
			result.put("flag", Constants.USER_PASSWORD_BLANK);
			return result;
		}
		
		if(StringUtils.isBlank(newenPassword)) {
			result.put("message", "新密码为空！");
			result.put("flag", Constants.USER_PASSWORD_BLANK);
			return result;
		}
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute(Constants.USER_LOGIN_MARK);
		
		String newPassword = rsaService.decrypt(newenPassword);
		String oldPassword = rsaService.decrypt(oldenPassword);
		
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
	public Map<String,Object> getUserInfo(HttpServletRequest request) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		HttpSession session = request.getSession();
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
	public Map<String,Object> modifyUserInfo(HttpServletRequest request) {
		
		String address = request.getParameter("address");
		String description = request.getParameter("description");
		
		String ssex = request.getParameter("sex");
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("version", 1);
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute(Constants.USER_LOGIN_MARK);
		
		if(StringUtils.isNotBlank(ssex)) {
			int sex = Integer.parseInt(ssex);
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
	public Map<String,Object> resetPassword(HttpServletRequest request) {
		
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		String enpassword = request.getParameter("password");
		
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
		
		HttpSession session = request.getSession();
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
