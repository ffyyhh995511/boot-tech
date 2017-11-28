package org.boot.tech.web.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;


public class BaseController implements HandlerInterceptor{
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> RESPONSE = new ThreadLocal<HttpServletResponse>();

	/**
	 * 拦截器之前
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		RESPONSE.set(response);
		REQUEST.set(request);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	
	/**
	 * 拦截器之后
	 */
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		REQUEST.remove();
		RESPONSE.remove();
	}
	
	/**
	 * 获取Request方法
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return REQUEST.get();
	}
	
	/**
	 * 获取Response方法
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return RESPONSE.get();
	}
	
	/**
	 * 打印参数
	 * @return
	 */
	public String getParameterMap(){
		Enumeration<String> enu= getRequest().getParameterNames();
		Map<String,Object> map = new HashMap<String, Object>();
		while(enu.hasMoreElements()){
			String key = (String) enu.nextElement();
			Object value = getRequest().getParameter(key);
			map.put(key, value);
		}
		if(map.size() > 0 ){
			return "params = " + JSON.toJSONString(map);
		}
		return null;
	}
	
	/**
	 * 打印head参数
	 * @return
	 */
	public String getHeadsMap(){
		Enumeration<String> enu= getRequest().getHeaderNames();
		Map<String,Object> map = new HashMap<String, Object>();
		while(enu.hasMoreElements()){
			String headerName=(String)enu.nextElement();
			String headerValue=getRequest().getHeader(headerName);//取出头信息内容
			map.put(headerName, headerValue);
		}
		if(map.size() > 0 ){
			return "heads = " + JSON.toJSONString(map);
		}
		return null;
	}
	
	/**
	 * 获取session方法
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	/**
	 * 校验参数是否null
	 * @param object
	 * @param parms
	 * @return
	 */
	public boolean isParamsNull(Object ...object){
		for (Object obj : object) {
			if(obj == null){
				return true;
			}
		}
		return false;
	}
	
}
