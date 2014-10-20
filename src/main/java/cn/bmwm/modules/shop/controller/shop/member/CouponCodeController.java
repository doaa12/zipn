/*


 * */
package cn.bmwm.modules.shop.controller.shop.member;

import javax.annotation.Resource;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.persistence.Pageable;
import cn.bmwm.common.utils.Message;
import cn.bmwm.modules.shop.controller.shop.BaseController;
import cn.bmwm.modules.shop.entity.Coupon;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.service.CouponCodeService;
import cn.bmwm.modules.shop.service.CouponService;
import cn.bmwm.modules.shop.service.MemberService;

/**
 * Controller - 会员中心 - 优惠码
 * 
 *
 * @version 1.0
 */
//@Controller("shopMemberCouponCodeController")
//@RequestMapping("/member/coupon_code")
public class CouponCodeController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;

	/**
	 * 兑换
	 */
	@RequestMapping(value = "/exchange", method = RequestMethod.GET)
	public String exchange(Integer pageNumber, ModelMap model) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("page", couponService.findPage(true, true, false, pageable));
		return "shop/member/coupon_code/exchange";
	}

	/**
	 * 兑换
	 */
	@RequestMapping(value = "/exchange", method = RequestMethod.POST)
	public @ResponseBody
	Message exchange(Long id) {
		Coupon coupon = couponService.find(id);
		if (coupon == null || !coupon.getIsEnabled() || !coupon.getIsExchange() || coupon.hasExpired()) {
			return ERROR_MESSAGE;
		}
		Member member = memberService.getCurrent();
		if (member.getPoint() < coupon.getPoint()) {
			return Message.warn("shop.member.couponCode.point");
		}
		couponCodeService.exchange(coupon, member);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer pageNumber, ModelMap model) {
		Member member = memberService.getCurrent();
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("page", couponCodeService.findPage(member, pageable));
		return "shop/member/coupon_code/list";
	}

}