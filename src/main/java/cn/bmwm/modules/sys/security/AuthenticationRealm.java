package cn.bmwm.modules.sys.security;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.bmwm.modules.shop.entity.Admin;
import cn.bmwm.modules.shop.entity.Shop;
import cn.bmwm.modules.shop.service.AdminService;
import cn.bmwm.modules.shop.service.CaptchaService;
import cn.bmwm.modules.sys.model.Setting;
import cn.bmwm.modules.sys.model.Setting.AccountLockType;
import cn.bmwm.modules.sys.model.Setting.CaptchaType;
import cn.bmwm.modules.sys.utils.SettingUtils;

/**
 * 权限认证
 * 
 * @version 1.0
 */
public class AuthenticationRealm extends AuthorizingRealm {

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	/**
	 * 获取认证信息
	 * 
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		AuthenticationToken authenticationToken = (AuthenticationToken) token;
		String username = authenticationToken.getUsername();
		String password = new String(authenticationToken.getPassword());
		String captchaId = authenticationToken.getCaptchaId();
		String captcha = authenticationToken.getCaptcha();
		String ip = authenticationToken.getHost();
		if (!captchaService.isValid(CaptchaType.adminLogin, captchaId, captcha)) {
			throw new UnsupportedTokenException();
		}
		if ((username != null) && (StringUtils.isNotBlank(password))) {
			Admin admin = adminService.findByUsername(username);
			if (admin == null) {
				throw new UnknownAccountException();
			}
			if (!admin.getIsEnabled()) {
				throw new DisabledAccountException();
			}
			Setting setting = SettingUtils.get();
			if (admin.getIsLocked()) {
				if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.admin)) {
					int loginFailureLockTime = setting.getAccountLockTime();
					if (loginFailureLockTime == 0) {
						throw new LockedAccountException();
					}
					Date lockedDate = admin.getLockedDate();
					Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					if (new Date().after(unlockDate)) {
						admin.setLoginFailureCount(0);
						admin.setIsLocked(false);
						admin.setLockedDate(null);
						adminService.update(admin);
					} else {
						throw new LockedAccountException();
					}
				} else {
					admin.setLoginFailureCount(0);
					admin.setIsLocked(false);
					admin.setLockedDate(null);
					adminService.update(admin);
				}
			}
			if (!DigestUtils.md5Hex(password).equals(admin.getPassword())) {
				int loginFailureCount = admin.getLoginFailureCount() + 1;
				if (loginFailureCount >= setting.getAccountLockCount()) {
					admin.setIsLocked(true);
					admin.setLockedDate(new Date());
				}
				admin.setLoginFailureCount(loginFailureCount);
				adminService.update(admin);
				throw new IncorrectCredentialsException();
			}
			
			admin.setLoginIp(ip);
			admin.setLoginDate(new Date());
			admin.setLoginFailureCount(0);
			adminService.update(admin);
			
			//zhoupuyue,增加商家店铺信息
			Shop shop = admin.getShop();
			Long shopId = shop == null ? null : shop.getId();
			
			return new SimpleAuthenticationInfo(new Principal(admin.getId(), username, shopId), password, getName());
			
		}
		throw new UnknownAccountException();
	}

	/**
	 * 获取授权信息
	 * 
	 * @param principals
	 *            principals
	 * @return 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			List<String> authorities = adminService.findAuthorities(principal.getId());
			if (authorities != null) {
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				authorizationInfo.addStringPermissions(authorities);
				return authorizationInfo;
			}
		}
		return null;
	}

}