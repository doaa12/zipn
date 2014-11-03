/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bmwm.modules.shop.service.MemberService;

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
	
	

}
