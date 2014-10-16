package cn.bmwm.modules.shop.controller.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

import cn.bmwm.common.utils.Constants;
import cn.bmwm.common.utils.WebUtils;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.service.MemberRankService;
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.shop.service.RSAService;
import cn.bmwm.modules.sys.exception.BusinessException;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.security.Principal;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * App -- 注册
 * @author zby
 * 2014-9-29 下午10:02:00
 */
@Controller
@RequestMapping(value = "/app/register")
public class RegisterController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	
	/**
	 * 检查用户名是否被禁用或已存在
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> check(String phone) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		if (StringUtils.isEmpty(phone)) {
			throw new BusinessException(" Parameter 'phone' can not be empty ! ");
		}
		
		result.put("version", 1);
		
		if (memberService.usernameDisabled(phone) || memberService.usernameExists(phone)) {
			result.put("flag", 1);
		} else {
			result.put("flag", 2);
		}
		
		return result;
		
	}
	
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
	 * 注册
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> register(String phone, String code, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		String password = rsaService.decryptParameter("enpassword", request);
		rsaService.removePrivateKey(request);
		
		/*
		Object ocode = session.getAttribute("code");
		
		if(ocode == null) {
			throw new BusinessException(" Parameter 'code' dose not exist ! ");
		}
		
		String scode = (String)ocode;
		
		if(!scode.equals(code)) {
			throw new BusinessException(" Parameter 'code' is invalid ! ");
		}
		*/
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		if(StringUtils.isBlank(phone)) {
			result.put("flag", Constants.USERNAME_BLANK);
			return result;
		}
		
		if(StringUtils.isBlank(password)) {
			result.put("flag", Constants.PASSWORD_BLANK);
			return result;
		}
		
		if (memberService.usernameExists(phone)) {
			result.put("flag", Constants.USERNAME_EXISTS);
			return result;
		}
		
		if(memberService.usernameDisabled(phone)) {
			result.put("flag", Constants.USERNAME_DISABLED);
			return result;
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
		member.setMemberRank(memberRankService.findDefault());
		member.setFavoriteProducts(null);
		memberService.save(member);
		
		result.put("flag", 1);
		
		session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), member.getUsername()));
		WebUtils.addCookie(request, response, Member.USERNAME_COOKIE_NAME, member.getUsername());
		
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
