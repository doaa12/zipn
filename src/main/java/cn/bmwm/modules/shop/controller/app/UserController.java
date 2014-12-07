/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Constants;
import cn.bmwm.common.Result;
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
	public Result changePassword(HttpSession session, String oldpassword, String newpassword) {
		
		if(StringUtils.isBlank(oldpassword)) {
			return new Result(Constants.USER_PASSWORD_BLANK, 1, "旧密码为空！");
		}
		
		if(StringUtils.isBlank(newpassword)) {
			return new Result(Constants.USER_PASSWORD_BLANK, 1, "新密码为空！");
		}
		
		Member member = (Member)session.getAttribute(Constants.USER_LOGIN_MARK);
		
		String newPassword = rsaService.decrypt(newpassword);
		String oldPassword = rsaService.decrypt(oldpassword);
		
		if (!DigestUtils.md5Hex(oldPassword).equals(member.getPassword())) {
			return new Result(Constants.USER_PASSWORD_ERROR, 1, "旧密码错误！");
		}
		
		member.setPassword(DigestUtils.md5Hex(newPassword));
		memberService.update(member);
		
		return new Result(Constants.SUCCESS, 1);
		
	}
	
	/**
	 * 获取用户信息
	 * @return
	 */
	@RequestMapping(value = "/get_userinfo", method = RequestMethod.POST)
	@ResponseBody
	public Result getUserInfo(HttpSession session) {
		
		Member member = (Member)session.getAttribute(Constants.USER_LOGIN_MARK);
		
		Result result = new Result(Constants.SUCCESS, 1);
		
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
	public Result modifyUserInfo(HttpSession session, String address, String description, Integer sex) {

		Member member = (Member)session.getAttribute(Constants.USER_LOGIN_MARK);
		
		if(sex != null) {
			member.setGender(sex == 1 ? Gender.male : Gender.female);
		}
		
		member.setAddress(address);
		member.setDescription(description);
		memberService.update(member);
		
		return new Result(Constants.SUCCESS, 1);
		
	}
	
	/**
	 * 重置密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reset_password", method = RequestMethod.POST)
	@ResponseBody
	public Result resetPassword(HttpSession session, String phone, String code, String enpassword) {

		if(StringUtils.isBlank(code)) {
			return new Result(Constants.USER_CODE_EMPTY, 1, "验证码为空！");
		}
		
		if(StringUtils.isBlank(phone)) {
			return new Result(Constants.USER_USERNAME_BLANK, 1, "手机号码为空！");
		}
		
		if(StringUtils.isBlank(enpassword)) {
			return new Result(Constants.USER_PASSWORD_BLANK, 1, "密码为空！");
		}
		
		Object ocode = session.getAttribute(Constants.VALIDATION_CODE);
		
		if(ocode == null) {
			return new Result(Constants.USER_CODE_ERROR, 1, "验证码错误！");
		}
		
		String scode = (String)ocode;
		
		if(!code.equals(scode)) {
			return new Result(Constants.USER_CODE_ERROR, 1, "验证码错误！");
		}
		
		String password = rsaService.decrypt(enpassword);
		
		Member member = memberService.findByUsername(phone);
		
		if(member == null) {
			return new Result(Constants.USER_USER_NOT_EXISTS, 1, "手机号码未注册！");
		}
		
		member.setPassword(DigestUtils.md5Hex(password));
		memberService.update(member);
		
		return new Result(Constants.SUCCESS, 1);
		
	}
	
}
