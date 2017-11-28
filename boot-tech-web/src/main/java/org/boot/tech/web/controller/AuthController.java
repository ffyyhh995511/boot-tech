package org.boot.tech.web.controller;

import org.boot.tech.web.annotation.RequestToken;
import org.boot.tech.web.annotation.Token;
import org.boot.tech.web.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/auth")
@Token(RequestToken.NEED)
public class AuthController extends BaseController{
	
	@Token(RequestToken.NEED)
	@RequestMapping(value="/test1",method=RequestMethod.GET)
	public Response<Object> test1(String token){
		return Response.successResponse();
	}
	
	@GetMapping("/test2")
	@Token(RequestToken.NEEDLESS)
	public Response<Object> test2(String token){
		return Response.successResponse();
	}
}
