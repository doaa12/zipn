/**
 * 
 */
package cn.bmwm.modules.shop.controller.app;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * App - 商品
 * @author zhoupuyue
 * @date 2014-8-27
 */
@Controller("appProductController")
@RequestMapping(value = "/app/product")
public class ProductController {

	/**
	 * 商品列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map<String,Object> list(Integer catId) {
		return null;
	}
	
}
