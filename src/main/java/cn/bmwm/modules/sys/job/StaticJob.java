package cn.bmwm.modules.sys.job;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.bmwm.modules.shop.service.StaticService;

/**
 * Job - 静态化
 * 
 * @version 1.0
 */
//@Component("staticJob")
//@Lazy(false)
public class StaticJob {

	//@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	/**
	 * 生成静态
	 */
	//@Scheduled(cron = "${job.static_build.cron}")
	public void build() {
		staticService.buildAll();
	}

}