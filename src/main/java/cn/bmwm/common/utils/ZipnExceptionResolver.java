/**
 * 
 */
package cn.bmwm.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import cn.bmwm.modules.sys.exception.BusinessException;

import com.alibaba.fastjson.JSONObject;

/**
 * 异常处理器
 * @author zhoupuyue
 * @date 2014-8-22
 */
public class ZipnExceptionResolver extends SimpleMappingExceptionResolver {
	
	public static Log log = LogFactory.getLog(ZipnExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		log.error("异常!", ex);
		
		// App请求
		if(request.getServletPath().startsWith("/app")){
			
			response.setContentType("application/json;charset=utf-8");
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("version", 1);
			map.put("flag", 0);
			
			if(ex instanceof BusinessException) {
				map.put("message", ex.getMessage());
			}else{
				map.put("message", " Server Error ! ");
			}
			
			try{
				
				PrintWriter writer = response.getWriter();
	            writer.write(JSONObject.toJSONString(map));  
	            writer.flush();
	            writer.close();
	            
	            return null;
	            
			}catch(IOException e){
				e.printStackTrace();
			}
			
		}
		
		return super.doResolveException(request, response, handler, ex);
		
	}
	
}
