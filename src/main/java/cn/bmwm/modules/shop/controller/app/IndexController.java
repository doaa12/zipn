package cn.bmwm.modules.shop.controller.app;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.modules.shop.controller.shop.BaseController;
import cn.bmwm.modules.shop.entity.ProductCategory;
import cn.bmwm.modules.shop.service.ProductCategoryService;


/**
 * App - 首页
 * @author zby
 * 2014-8-25 下午8:49:00
 */
@Controller("appIndexController")
@RequestMapping(value = "/app/index")
public class IndexController extends BaseController {
	
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;

	/**
	 * App 首页请求
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public String index() {
		List<ProductCategory> productCategories = productCategoryService.findTree();
		return "";
	}
	
}
