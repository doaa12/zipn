package cn.bmwm.common.utils;

/**
 * 公共参数
 * 
 * @version 1.0
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd",
			"yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** application.xml文件路径 */
	public static final String APPLICATION_XML_PATH = "/application.xml";

	/** application.properties文件路径 */
	public static final String APPLICATION_PROPERTIES_PATH = "/application.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}