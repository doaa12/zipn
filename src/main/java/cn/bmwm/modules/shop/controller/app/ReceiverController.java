/*


 * */
package cn.bmwm.modules.shop.controller.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bmwm.common.Constants;
import cn.bmwm.common.Result;
import cn.bmwm.modules.shop.controller.app.vo.ReceiverVo;
import cn.bmwm.modules.shop.controller.shop.BaseController;
import cn.bmwm.modules.shop.entity.Area;
import cn.bmwm.modules.shop.entity.Member;
import cn.bmwm.modules.shop.entity.Receiver;
import cn.bmwm.modules.shop.service.AreaService;
import cn.bmwm.modules.shop.service.MemberService;
import cn.bmwm.modules.shop.service.ReceiverService;

/**
 * Controller - 会员中心 - 收货地址
 * 
 *
 * @version 1.0
 */
@Controller("appReceiverController")
@RequestMapping("/app/receiver")
public class ReceiverController extends BaseController {
	
	/**
	 * 收货地址为空
	 */
	public static final int RECEIVE_ADDRESS_EMPTY = 101;
	
	/**
	 * 手机号码为空
	 */
	public static final int RECEIVE_PHONE_EMPTY = 102;
	
	/**
	 * 地区为空
	 */
	public static final int RECEIVE_AREA_EMPTY = 103;
	
	/**
	 * 地区不存在
	 */
	public static final int RECEIVE_AREA_NOT_EXISTS = 104;
	
	/**
	 * 超过最大数量
	 */
	public static final int RECEIVE_MAX_NUMBER = 105;
	
	/**
	 * ID为空
	 */
	public static final int RECEIVE_ID_EMPTY = 106;
	
	/**
	 * ID不存在
	 */
	public static final int RECEIVE_ID_NOT_EXISTS = 107;
	
	/**
	 * 非法的操作
	 */
	public static final int RECEIVE_INVALID_REQUEST = 108;
	
	/**
	 * 收货人为空
	 */
	public static final int RECEIVE_CONSIGNEE_EMPTY = 109;

	/**
	 * 每页记录数
	 */
	public static final int PAGE_SIZE = 5;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;

	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(Receiver receiver, Long areaId) {
		
		if(StringUtils.isBlank(receiver.getAddress())) {
			return new Result(RECEIVE_ADDRESS_EMPTY, 1, "详细地址为空！");
		}
		
		if(StringUtils.isBlank(receiver.getPhone())) {
			return new Result(RECEIVE_PHONE_EMPTY, 1, "手机号码为空！");
		}
		
		if(StringUtils.isBlank(receiver.getConsignee())) {
			return new Result(RECEIVE_CONSIGNEE_EMPTY, 1, "收货人为空！");
		}
		
		if(areaId == null) {
			return new Result(RECEIVE_AREA_EMPTY, 1, "地区为空！");
		}
		
		Area area = areaService.find(areaId);
		
		if(area == null) {
			return new Result(RECEIVE_AREA_NOT_EXISTS, 1, "地区不存在！");
		}
		
		receiver.setArea(area);
		
		Member member = memberService.getAppCurrent();
		long count = receiverService.count(member);
		
		if (Receiver.MAX_RECEIVER_COUNT != null && count >= Receiver.MAX_RECEIVER_COUNT) {
			return new Result(RECEIVE_MAX_NUMBER, 1, "收货地址数量已达到上限！");
		}
		
		if(receiver.getIsDefault() == null) {
			receiver.setIsDefault(false);
		}
		
		receiver.setMember(member);
		receiverService.save(receiver);
		
		return new Result(Constants.SUCCESS, 1);
		
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(Receiver receiver, Long areaId) {
		
		if(StringUtils.isBlank(receiver.getAddress())) {
			return new Result(RECEIVE_ADDRESS_EMPTY, 1, "详细地址为空！");
		}
		
		if(StringUtils.isBlank(receiver.getPhone())) {
			return new Result(RECEIVE_PHONE_EMPTY, 1, "手机号码为空！");
		}
		
		if(StringUtils.isBlank(receiver.getConsignee())) {
			return new Result(RECEIVE_CONSIGNEE_EMPTY, 1, "收货人为空！");
		}
		
		if(areaId == null) {
			return new Result(RECEIVE_AREA_EMPTY, 1, "地区为空！");
		}
		
		Area area = areaService.find(areaId);
		
		if(area == null) {
			return new Result(RECEIVE_AREA_NOT_EXISTS, 1, "地区不存在！");
		}
		
		receiver.setArea(area);
		
		if(receiver.getId() == null) {
			return new Result(RECEIVE_ID_EMPTY, 1, "'ID'为空！");
		}
		
		Receiver preceiver = receiverService.find(receiver.getId());
		
		if(preceiver == null) {
			return new Result(RECEIVE_ID_NOT_EXISTS, 1, "'ID'不存在！");
		}
		
		Member member = memberService.getAppCurrent();
		
		if (!member.equals(preceiver.getMember())) {
			return new Result(RECEIVE_INVALID_REQUEST, 1, "非法的操作！");
		}
		
		if(receiver.getIsDefault() == null) {
			receiverService.update(receiver, "member", "isDefault");
		}else {
			receiverService.update(receiver, "member");
		}
		
		return new Result(Constants.SUCCESS, 1);
		
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Result delete(Long id) {
		
		if(id == null) {
			return new Result(RECEIVE_ID_EMPTY, 1, "'ID'为空！");
		}
		
		Receiver receiver = receiverService.find(id);
		
		if (receiver == null) {
			return new Result(RECEIVE_ID_NOT_EXISTS, 1, "'ID'不存在！");
		}
		
		Member member = memberService.getAppCurrent();
		
		if (!member.equals(receiver.getMember())) {
			return new Result(RECEIVE_INVALID_REQUEST, 1, "非法的操作！");
		}
		
		receiverService.delete(id);
		
		return new Result(Constants.SUCCESS, 1);
		
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result list(Integer page) {
		
		Member member = memberService.getAppCurrent();
		int offset = 0;
		
		if(page != null) {
			if(page < 1) page = 1;
			offset = (page - 1) * PAGE_SIZE;
		}
		
		List<Receiver> list = receiverService.findList(member, offset, PAGE_SIZE);
		
		return new Result(Constants.SUCCESS, 1, "", getReceiverList(list));
		
	}
	
	/**
	 * 转换数据类型
	 * @param receiveList
	 * @return
	 */
	private List<ReceiverVo> getReceiverList(List<Receiver> receiveList) {
		
		List<ReceiverVo> list = new ArrayList<ReceiverVo>();
		
		if(receiveList == null || receiveList.size() == 0) {
			return list;
		}
		
		for(Receiver receiver : receiveList) {
			ReceiverVo vo = new ReceiverVo();
			BeanUtils.copyProperties(receiver, vo);
			list.add(vo);
		}
		
		return list;
		
	}

}