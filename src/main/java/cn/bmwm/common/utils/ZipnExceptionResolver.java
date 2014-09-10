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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import cn.bmwm.modules.shop.controller.admin.BaseController;
import cn.bmwm.modules.sys.exception.BusinessException;
import cn.bmwm.modules.sys.exception.SystemException;

import com.alibaba.fastjson.JSONObject;

/**
 * 异常处理器
 * @author zhoupuyue
 * @date 2014-8-22
 */
public class ZipnExceptionResolver extends SimpleMappingExceptionResolver {
	
	public static Log log = LogFactory.getLog(ZipnExceptionResolver.class);
	
	public static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		log.error("异常!", ex);
		
		// App请求
		if(request.getServletPath().startsWith("/app/")){
			
			response.setContentType("application/json;charset=utf-8");
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("version", 1);
			map.put("flag", 0);
			
			if(ex instanceof BusinessException || ex instanceof SystemException) {
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
		
		//后台管理
		}else if(request.getServletPath().startsWith("/admin/") || request.getServletPath().startsWith("/shopadmin/")) {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			if(ex instanceof BusinessException || ex instanceof SystemException) {
				requestAttributes.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ex.getMessage(), RequestAttributes.SCOPE_REQUEST);
			}else{
				requestAttributes.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Server Error ! ", RequestAttributes.SCOPE_REQUEST);
			}
			return new ModelAndView(BaseController.ERROR_VIEW);
		}
		
		return super.doResolveException(request, response, handler, ex);
		
	}
	
}
