package cn.bmwm.modules.sys.listener;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import cn.bmwm.modules.shop.service.CacheService;
import cn.bmwm.modules.shop.service.SearchService;
import cn.bmwm.modules.shop.service.StaticService;

/**
 * Listener - 初始化
 * 
 * @version 1.0
 */
@Component("initListener")
public class InitListener implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {

	/** 安装初始化配置文件 */
	private static final String INSTALL_INIT_CONFIG_FILE_PATH = "/install_init.conf";

	/** servletContext */
	private ServletContext servletContext;

	@Resource(name = "staticServiceImpl")
	private StaticService staticService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (servletContext != null && contextRefreshedEvent.getApplicationContext().getParent() == null) {
			File installInitConfigFile = new File(servletContext.getRealPath(INSTALL_INIT_CONFIG_FILE_PATH));
			if (installInitConfigFile.exists()) {
				cacheService.clear();
				//staticService.buildAll();
				searchService.purge();
				searchService.index();
				installInitConfigFile.delete();
			} else {
				//staticService.buildIndex();
				//staticService.buildOther();
			}
		}
	}

}