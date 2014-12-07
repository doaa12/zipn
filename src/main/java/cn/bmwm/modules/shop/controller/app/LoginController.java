package cn.bmwm.modules.shop.controller.app;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
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
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.model.Setting.AccountLockType;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * @author zby
 * 2014-11-6 下午9:09:04
 */
@Controller
public class LoginController {
	
	public static final Log log = LogFactory.getLog(LoginController.class);
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	
	/**
	 * 登录
	 * @param phone
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/app/user/login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(HttpServletRequest request, HttpSession session, String phone, String enpassword) {

		if(StringUtils.isBlank(phone)) {
			return new Result(Constants.USER_USERNAME_BLANK, 1, "手机号码为空！");
		}
		
		if(StringUtils.isBlank(enpassword)) {
			return new Result(Constants.USER_PASSWORD_BLANK, 1, "密码为空！");
		}
		
		String password = rsaService.decrypt(enpassword);
		
		if(StringUtils.isBlank(password)) {
			return new Result(Constants.USER_PASSWORD_BLANK, 1, "密码为空！");
		}
		
		Member member = memberService.findByUsername(phone);
		
		if (member == null) {
			return new Result(Constants.USER_USER_NOT_EXISTS, 1, "手机号码未注册！");
		}
		
		if (!member.getIsEnabled()) {
			return new Result(Constants.USER_USER_DISABLED, 1, "手机号码已禁用！");
		}
		
		Setting setting = SettingUtils.get();
		
		if (member.getIsLocked()) {
			
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.member)) {
				
				int loginFailureLockTime = setting.getAccountLockTime();
				if (loginFailureLockTime == 0) {
					return new Result(Constants.USER_USER_LOCKED, 1, "手机号码已锁定！");
				}
				
				Date lockedDate = member.getLockedDate();
				Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
				
				if (new Date().after(unlockDate)) {
					member.setLoginFailureCount(0);
					member.setIsLocked(false);
					member.setLockedDate(null);
					memberService.update(member);
				} else {
					return new Result(Constants.USER_USER_LOCKED, 1, "手机号码已锁定！");
				}
				
			} else {
				member.setLoginFailureCount(0);
				member.setIsLocked(false);
				member.setLockedDate(null);
				memberService.update(member);
			}
			
		}
		
		if (!DigestUtils.md5Hex(password).equals(member.getPassword())) {
			
			int loginFailureCount = member.getLoginFailureCount() + 1;
			if (loginFailureCount >= setting.getAccountLockCount()) {
				member.setIsLocked(true);
				member.setLockedDate(new Date());
			}
			
			member.setLoginFailureCount(loginFailureCount);
			memberService.update(member);
			
			return new Result(Constants.USER_PASSWORD_ERROR, 1, "密码错误！");
			
		}
		
		member.setLoginIp(request.getRemoteAddr());
		member.setLoginDate(new Date());
		member.setLoginFailureCount(0);
		memberService.update(member);
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		Enumeration<?> keys = session.getAttributeNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			attributes.put(key, session.getAttribute(key));
		}
		
		session.invalidate();
		session = request.getSession();
		
		for (Entry<String, Object> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}

		Result result = new Result(Constants.SUCCESS, 1);
		result.put(Constants.USER_LOGIN_MARK, DigestUtils.md5Hex(member.getId().toString() + DigestUtils.md5Hex(password)) + "@" + member.getId().toString());
		result.put("username", member.getUsername());
		
		return result;
		
	}
	
}
