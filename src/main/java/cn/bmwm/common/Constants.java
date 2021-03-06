/**
 * 
 */
package cn.bmwm.common;

/**
 * 常量类
 * @author zhoupuyue
 * @date 2014-10-16
 */
public class Constants {
	
	/**
	 * 登录标识
	 */
	public static final String USER_LOGIN_MARK = "principal";
	
	/**
	 * 短信验证码
	 */
	public static final String VALIDATION_CODE = "validation-code";
	
	/**
	 * 处理完成
	 */
	public static final int SUCCESS = 1;
	
	/**
	 * 处理失败
	 */
	public static final int FAILED = 0;
	
	
	//----------------------- 用户 -----------------------
	
	/**
	 * 手机号码为空
	 */
	public static final int USER_USERNAME_BLANK = 101;
	
	/**
	 * 密码为空
	 */
	public static final int USER_PASSWORD_BLANK = 102;

	/**
	 * 手机号码未注册
	 */
	public static final int USER_USER_NOT_EXISTS = 103;
	
	/**
	 * 账号被冻结
	 */
	public static final int USER_USER_DISABLED = 104;
	
	/**
	 * 密码错误
	 */
	public static final int USER_PASSWORD_ERROR = 105;
	
	/**
	 * 账号被锁定
	 */
	public static final int USER_USER_LOCKED = 106;
	
	/**
	 * 用户名已存在
	 */
	public static final int USER_USERNAME_EXISTS = 107;
	
	/**
	 * 用户名被禁用
	 */
	public static final int USER_USERNAME_DISABLED = 108;
	
	/**
	 * 验证码为空
	 */
	public static final int USER_CODE_EMPTY = 109;
	
	/**
	 * 验证码错误
	 */
	public static final int USER_CODE_ERROR = 110;
	
	
	//----------------------- 购物车 -----------------------
	
	/**
	 * 购物车商品数量小于1
	 */
	public static final int CART_QUANTITY_ERROR = 121;
	
	/**
	 * 商品不存在
	 */
	public static final int CART_PRODUCT_NOT_EXISTS = 122;
	
	/**
	 * 商品未上架
	 */
	public static final int CART_PRODUCT_NOT_MARKETABLE = 123;
	
	/**
	 * 该商品是赠品
	 */
	public static final int CART_PRODUCT_GIFT = 124;
	
	/**
	 * 超出购物车商品最大数量
	 */
	public static final int CART_PRODUCT_MAX_COUNT = 125;
	
	/**
	 * 超出单品允许最大数量
	 */
	public static final int CART_ITEM_MAX_QUANTITY = 126;
	
	/**
	 * 超出库存数量
	 */
	public static final int CART_PRODUCT_STOCK_QUANTITY = 127;
	
	/**
	 * 购物车为空
	 */
	public static final int CART_CART_EMPTY = 128;
	
	/**
	 * 购物车项不存在
	 */
	public static final int CART_CART_ITEM_NOT_EXISTS = 129;
	
	/**
	 * 购物车商品是否选择为空
	 */
	public static final int CART_ITEM_ISSELECTED_EMPTY = 130;
	
}
