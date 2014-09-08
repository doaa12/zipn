package cn.bmwm.modules.sys.exception;

/**
 * 系统异常
 * @author zby
 * 2014-9-8 下午11:20:37
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 94866178849588338L;

	/**
	 * 
	 */
	public SystemException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SystemException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SystemException(Throwable cause) {
		super(cause);
	}

}
