package cn.bmwm.modules.sys.job;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.bmwm.modules.shop.service.CartService;

/**
 * Job - 购物车
 * 
 * @version 1.0
 */
@Component("cartJob")
@Lazy(false)
public class CartJob {

	@Resource(name = "cartServiceImpl")
	private CartService cartService;

	/**
	 * 清除过期购物车
	 */
	@Scheduled(cron = "${job.cart_evict_expired.cron}")
	public void evictExpired() {
		cartService.evictExpired();
	}

}