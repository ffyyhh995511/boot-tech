package org.boot.tech.web.controller;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.boot.tech.web.exception.BussinessException;
import org.boot.tech.web.exception.handler.ExceptionsHandler;
import org.boot.tech.web.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试全局异常捕获
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value="/ex")
public class ExceptionsHandlerController {
	
	@GetMapping(path = "/test/1.0/") // Map ONLY GET Requests
	public Object complain(String mobile) throws ExecutionException {
		if(mobile == null){
			throw new NullPointerException("空指针了。。。。。。。。");
		}
		return Response.successResponse("200 , 提交成功");
	}
	
	@GetMapping(path = "/test/2.0/") // Map ONLY GET Requests
	public Object complain2(String mobile) throws BussinessException {
		if(mobile == null){
			throw new BussinessException("自定义报错了。。。。。。。。");
		}
		return Response.successResponse("200 , 提交成功");
	}
}
