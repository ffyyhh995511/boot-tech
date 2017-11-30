package org.boot.tech.web.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.boot.tech.web.exception.BussinessException;
import org.boot.tech.web.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description 异常处理中心
 *
 * @author Ranger
 * @version 2017年7月12日_下午1:59:46
 *
 */
@ControllerAdvice
public class ExceptionsHandler {
    Logger log = LoggerFactory.getLogger(this.getClass());
    
    @ResponseBody
	@ExceptionHandler(NullPointerException.class)
	Object handlerNullPointerException(HttpServletRequest request, NullPointerException e) {
		log.error(e.getMessage(), e);
        return Response.interiorErrorResponse();
	}
    
    @ResponseBody
	@ExceptionHandler(BussinessException.class)
	Object handlerBussinessException(HttpServletRequest request, BussinessException e) {
		log.error(e.getMessage(), e);
        return Response.interiorErrorResponse();
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	Object handlerException(HttpServletRequest request, Exception e) {
		log.error(e.getMessage(), e);
        return Response.interiorErrorResponse();
	}
}