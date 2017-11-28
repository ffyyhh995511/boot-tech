package org.boot.tech.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.boot.tech.web.response.Response;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

public class BaseInterceptor implements HandlerInterceptor{
	
	protected void sendErrorMsg(HttpServletResponse response, String msg, Object data) throws IOException {
		Response<Object> res = null;
		if(msg == null && data == null){
			res = Response.AuthResponse();
		}else{
			res = Response.AuthResponse(data,msg);
		}
		String result = JSON.toJSONString(res);
		response.setHeader("Content-Type", "application/json; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
	}
	
	protected void sendAuthErrorMsg(HttpServletResponse response) throws IOException {
		sendErrorMsg(response, null, null);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
