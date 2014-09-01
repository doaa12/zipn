/**
 * 
 */
package cn.bmwm.modules.sys.exception;

/**
 * 业务异常
 * @author zhoupuyue
 * @date 2014-9-1
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7069116328002802970L;

	/**
	 * 
	 */
	public BusinessException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public BusinessException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public BusinessException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}

}
