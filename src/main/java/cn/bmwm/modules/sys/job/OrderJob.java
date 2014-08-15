package cn.bmwm.modules.sys.job;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.bmwm.modules.shop.service.OrderService;

/**
 * Job - 订单
 * 
 * @version 1.0
 */
@Component("orderJob")
@Lazy(false)
public class OrderJob {

	@Resource(name = "orderServiceImpl")
	private OrderService orderService;

	/**
	 * 释放过期订单库存
	 */
	@Scheduled(cron = "${job.order_release_stock.cron}")
	public void releaseStock() {
		orderService.releaseStock();
	}

}