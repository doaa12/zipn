package cn.bmwm.common;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 返回结果
 * @author zby
 * 2014-12-7 下午7:07:35
 */
public class Result extends HashMap<String,Object> implements Serializable {
	
	private static final long serialVersionUID = -1385662450638309282L;

	private int version = 1;

	private int flag;
	
	private String message;
	
	private Object data;
	
	public Result() {
		
	}
	
	public Result(int flag) {
		this.flag = flag;
	}
	
	public Result(int flag, int version) {
		this.flag = flag;
		this.version = version;
	}
	
	public Result(int flag, int version, String message) {
		this.flag = flag;
		this.version = version;
		this.message = message;
	}
	
	public Result(int flag, int version, String message, Object data) {
		this.flag = flag;
		this.version = version;
		this.message = message;
		this.data = data;
	}
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
