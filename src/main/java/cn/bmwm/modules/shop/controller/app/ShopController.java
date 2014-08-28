/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * App - 店铺
 * @author zhoupuyue
 * @date 2014-8-28
 */
@Controller("appShopController")
@RequestMapping(value = "/app/shop")
public class ShopController {
	
	/**
	 * 店铺列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map<String,Object> list(Integer catId) {
		return null;
	}
	
}
