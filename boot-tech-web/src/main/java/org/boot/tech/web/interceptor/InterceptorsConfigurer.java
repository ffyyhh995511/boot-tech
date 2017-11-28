package org.boot.tech.web.interceptor;

import javax.annotation.Resource;

import org.boot.tech.web.controller.BaseController;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorsConfigurer extends WebMvcConfigurerAdapter{

	@Resource
	private AuthInterceptor authInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new BaseController()).addPathPatterns("/**");
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
	}

}