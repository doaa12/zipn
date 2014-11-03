package cn.bmwm.modules.sys.exception;

/**
 * 非法的状态异常
 * @author zby
 * 2014-11-3 下午10:36:32
 */
public class IllegalUserStatusException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7574688431548537835L;

	/**
	 * 
	 */
	public IllegalUserStatusException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public IllegalUserStatusException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IllegalUserStatusException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public IllegalUserStatusException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public IllegalUserStatusException(Throwable cause) {
		super(cause);
	}
	
}
