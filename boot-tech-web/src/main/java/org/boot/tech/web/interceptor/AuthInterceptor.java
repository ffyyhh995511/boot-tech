package org.boot.tech.web.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.boot.tech.core.service.AuthService;
import org.boot.tech.web.annotation.RequestToken;
import org.boot.tech.web.annotation.Token;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class AuthInterceptor extends BaseInterceptor{
	
	private boolean noRequireToken = true;
	
	private boolean requireToken = false;
	
	@Resource
	AuthService authService;

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = ((HandlerMethod) handler);
			Token classTokenRequire = method.getBeanType().getAnnotation(Token.class);
			Token methodTokenRequire = method.getMethodAnnotation(Token.class);
			if (methodTokenRequire == null && classTokenRequire == null) {//类和方法上都为空，返回默认配置
				return noRequireToken;
			}else if (methodTokenRequire == null && classTokenRequire != null) {//方法上没有指定，按照类上面的指定进行
				//类上面有标记
				RequestToken value = classTokenRequire.value();
				return chargeAuth(value, request, response);
			}else if(methodTokenRequire != null) {//优先对方法进行
				RequestToken value = methodTokenRequire.value();
				return chargeAuth(value, request, response);
			}
		}
		return noRequireToken;
	}

	private boolean chargeAuth(RequestToken requestToken, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(RequestToken.NEEDLESS == requestToken){
			return true;
		}
		Map<String, String[]> map = request.getParameterMap();
		String[] uidCells = map.get("uid");
		String[] tokenCells = map.get("token");
		String[] signCells = map.get("sign");
		if(!StringUtils.isAnyEmpty(tokenCells)){
			//业务逻辑判断
			boolean checkToken = authService.checkToken(tokenCells[0]);
			if(checkToken){
				return true;
			}
		}
		sendAuthErrorMsg(response);
		return false;
	}
	
}
