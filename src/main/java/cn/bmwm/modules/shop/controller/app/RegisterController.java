package cn.bmwm.modules.shop.controller.app;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Constants;
import cn.bmwm.common.Result;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.shop.service.RSAService;
import cn.bmwm.modules.sys.exception.BusinessException;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * App -- 注册
 * @author zby
 * 2014-9-29 下午10:02:00
 */
@Controller
@RequestMapping(value = "/app/user")
public class RegisterController {
	
	public static final Log log = LogFactory.getLog(RegisterController.class);
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	
	/**
	 * 检查用户名是否被禁用或已存在
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Result check(String phone) {
		
		if (StringUtils.isEmpty(phone)) {
			throw new BusinessException(" Parameter 'phone' can not be empty ! ");
		}
		
		if (memberService.usernameDisabled(phone)) {
			return new Result(2, 1, "手机号码已禁用！");
		}else if(memberService.usernameExists(phone)){
			return new Result(3, 1, "手机号码已注册！");
		}
		
		return new Result(Constants.SUCCESS, 1);
		
	}
	
	/**
	 * 注册
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Result register(String phone, String code, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		if(StringUtils.isBlank(phone)) {
			return new Result(Constants.USER_USERNAME_BLANK, 1, "手机号码为空！");
		}
		
		String enpassword = request.getParameter("enpassword");
		
		if(StringUtils.isBlank(enpassword)) {
			return new Result(Constants.USER_PASSWORD_BLANK, 1, "密码为空！");
		}
		
		if(StringUtils.isBlank(code)) {
			return new Result(Constants.USER_CODE_EMPTY, 1, "验证码为空！");
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
		
		if(StringUtils.isBlank(password)) {
			return new Result(Constants.USER_PASSWORD_BLANK, 1, "密码为空！");
		}
		
		if (memberService.usernameExists(phone)) {
			return new Result(Constants.USER_USERNAME_EXISTS, 1, "该手机号码已注册！");
		}
		
		if(memberService.usernameDisabled(phone)) {
			return new Result(Constants.USER_USERNAME_DISABLED, 1, "该手机号码已被禁用！");
		}
		
		Setting setting = SettingUtils.get();
		
		Member member = new Member();
		
		member.setUsername(phone);
		member.setPassword(DigestUtils.md5Hex(password));
		member.setPoint(setting.getRegisterPoint());
		member.setAmount(new BigDecimal(0));
		member.setBalance(new BigDecimal(0));
		member.setIsEnabled(true);
		member.setIsLocked(false);
		member.setLoginFailureCount(0);
		member.setLockedDate(null);
		member.setRegisterIp(request.getRemoteAddr());
		member.setLoginIp(request.getRemoteAddr());
		member.setLoginDate(new Date());
		member.setSafeKey(null);
		//member.setMemberRank(memberRankService.findDefault());
		member.setFavoriteProducts(null);
		memberService.save(member);
		
		Result result = new Result(Constants.SUCCESS, 1);
		result.put(Constants.USER_LOGIN_MARK, DigestUtils.md5Hex(member.getId().toString() + DigestUtils.md5Hex(password)) + "@" + member.getId().toString());
		result.put("username", member.getUsername());
		
		return result;
		
	}
	
}
