package cn.bmwm.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author zby
 * 2014-11-30 下午9:27:51
 */
public class DateUtils {
	
	/**
	 * 格式化时间
	 * @param time
	 * @return
	 */
	public static String formatTime(Date time) {
		if(time == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(time);
	}

}
