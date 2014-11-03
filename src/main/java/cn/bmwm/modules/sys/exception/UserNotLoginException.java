package cn.bmwm.modules.sys.exception;

/**
 * 用户未登录异常
 * @author zby
 * 2014-11-3 下午10:26:26
 */
public class UserNotLoginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6385355340430910352L;

	/**
	 * 
	 */
	public UserNotLoginException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UserNotLoginException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UserNotLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public UserNotLoginException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UserNotLoginException(Throwable cause) {
		super(cause);
	}
	

}
