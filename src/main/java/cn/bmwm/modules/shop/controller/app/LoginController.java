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

import cn.bmwm.common.utils.Constants;
import cn.bmwm.common.utils.WebUtils;
import cn.bmwm.modules.shop.entity.Cart;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.service.CartService;
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.shop.service.RSAService;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.model.Setting.AccountLockType;
import cn.bmwm.modules.sys.security.Principal;
import cn.bmwm.modules.sys.utils.SettingUtils;


/**
 * App -- 登录
 * @author zby
 * 2014-9-29 下午9:52:06
 */
@Controller
@RequestMapping(value = "/app/login")
public class LoginController {
	
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	
	/**
	 * 登录
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public Map<String,Object> login(String phone, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		String password = rsaService.decryptParameter("enpassword", request);
		rsaService.removePrivateKey(request);
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		if(StringUtils.isBlank(phone)) {
			result.put("flag", Constants.USERNAME_BLANK);
			return result;
		}
		
		if(StringUtils.isBlank(password)) {
			result.put("flag", Constants.PASSWORD_BLANK);
			return result;
		}
		
		Member member = memberService.findByUsername(phone);
		
		if (member == null) {
			result.put("flag", Constants.USER_NOT_EXISTS);
			return result;
		}
		
		if (!member.getIsEnabled()) {
			result.put("flag", Constants.USER_DISABLED);
			return result;
		}
		
		Setting setting = SettingUtils.get();
		
		if (member.getIsLocked()) {
			
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.member)) {
				
				int loginFailureLockTime = setting.getAccountLockTime();
				if (loginFailureLockTime == 0) {
					result.put("flag", Constants.USER_LOCKED);
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
					result.put("flag", Constants.USER_LOCKED);
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
			
			result.put("flag", Constants.PASSWORD_ERROR);
			return result;
			
		}
		
		member.setLoginIp(request.getRemoteAddr());
		member.setLoginDate(new Date());
		member.setLoginFailureCount(0);
		memberService.update(member);

		Cart cart = cartService.getCurrent();
		if (cart != null) {
			if (cart.getMember() == null) {
				cartService.merge(member, cart);
				WebUtils.removeCookie(request, response, Cart.ID_COOKIE_NAME);
				WebUtils.removeCookie(request, response, Cart.KEY_COOKIE_NAME);
			}
		}

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

		session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), phone));
		WebUtils.addCookie(request, response, Member.USERNAME_COOKIE_NAME, member.getUsername());

		return null;
		
	}
	
}
