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

	public Result() {
		
	}
	
	public Result(int flag) {
		this.put("flag", flag);
	}
	
	public Result(int flag, int version) {
		this.put("flag", flag);
		this.put("version", version);
	}
	
	public Result(int flag, int version, String message) {
		this.put("flag", flag);
		this.put("version", version);
		this.put("message", message);
	}
	
	public Result(int flag, int version, String message, Object data) {
		this.put("flag", flag);
		this.put("version", version);
		this.put("message", message);
		this.put("data", data);
	}
	
	public void setFlag(int flag) {
		this.put("flag", flag);
	}

	public void setMessage(String message) {
		this.put("message", message);
	}

	public void setData(Object data) {
		this.put("data", data);
	}

	public void setVersion(int version) {
		this.put("version", version);
	}

}
