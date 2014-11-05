/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.utils.Constants;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Member.Gender;
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.shop.service.RSAService;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.model.Setting.AccountLockType;
import cn.bmwm.modules.sys.utils.SettingUtils;

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
	
	@RequestMapping(value = "/execute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> execute(String apiName, HttpServletRequest request, HttpServletResponse response) {
		
		if("changePasword".equals(apiName)) {
			return changePassword(request, response);
		} else if("getUserInfo".equals(apiName)) {
			return getUserInfo(request);
		} else if("modifyUserInfo".equals(apiName)) {
			return modifyUserInfo(request);
		} else if("login".equals(apiName)) {
			return login(request);
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
			result.put("flag", Constants.USER_PASSWORD_BLANK);
			return result;
		}
		
		if(StringUtils.isBlank(newenPassword)) {
			result.put("flag", Constants.USER_PASSWORD_BLANK);
			return result;
		}
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute(Constants.USER_LOGIN_MARK);
		
		String newPassword = rsaService.decrypt(newenPassword);
		String oldPassword = rsaService.decrypt(oldenPassword);
		
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
	 * 登录
	 * @param phone
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public Map<String,Object> login(HttpServletRequest request) {
		
		String phone = request.getParameter("phone");
		String enPassword = request.getParameter("enpassword");
		
		HttpSession session = request.getSession();
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("version", 1);
		
		if(StringUtils.isBlank(phone)) {
			result.put("flag", Constants.USER_USERNAME_BLANK);
			return result;
		}
		
		if(StringUtils.isBlank(enPassword)) {
			result.put("flag", Constants.USER_PASSWORD_BLANK);
			return result;
		}
		
		String password = rsaService.decrypt(enPassword);
		
		if(StringUtils.isBlank(password)) {
			result.put("flag", Constants.USER_PASSWORD_BLANK);
			return result;
		}
		
		Member member = memberService.findByUsername(phone);
		
		if (member == null) {
			result.put("flag", Constants.USER_USER_NOT_EXISTS);
			return result;
		}
		
		if (!member.getIsEnabled()) {
			result.put("flag", Constants.USER_USER_DISABLED);
			return result;
		}
		
		Setting setting = SettingUtils.get();
		
		if (member.getIsLocked()) {
			
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.member)) {
				
				int loginFailureLockTime = setting.getAccountLockTime();
				if (loginFailureLockTime == 0) {
					result.put("flag", Constants.USER_USER_LOCKED);
					return result;
				}
				
				Date lockedDate = member.getLockedDate();
				Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
				
				if (new Date().after(unlockDate)) {
					member.setLoginFailureCount(0);
					member.setIsLocked(false);
					member.setLockedDate(null);
					memberService.update(member);
				} else {
					result.put("flag", Constants.USER_USER_LOCKED);
					return result;
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
			
			result.put("flag", Constants.USER_PASSWORD_ERROR);
			
			return result;
			
		}
		
		member.setLoginIp(request.getRemoteAddr());
		member.setLoginDate(new Date());
		member.setLoginFailureCount(0);
		memberService.update(member);
		
		/*
		Cart cart = cartService.getCurrent();
		if (cart != null) {
			if (cart.getMember() == null) {
				cartService.merge(member, cart);
				WebUtils.removeCookie(request, response, Cart.ID_COOKIE_NAME);
				WebUtils.removeCookie(request, response, Cart.KEY_COOKIE_NAME);
			}
		}
		*/
		
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

		//session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), phone));
		//WebUtils.addCookie(request, response, Member.USERNAME_COOKIE_NAME, member.getUsername());
		
		result.put(Constants.USER_LOGIN_MARK, DigestUtils.md5Hex(member.getId().toString()) + "@" + member.getId().toString());
		result.put(Constants.USER_LOGIN_TIME, System.currentTimeMillis());
		result.put("username", member.getUsername());
		result.put("flag", 1);
		
		return result;
		
	}
	

}
