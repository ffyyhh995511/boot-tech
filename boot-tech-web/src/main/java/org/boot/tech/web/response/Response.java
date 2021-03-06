package org.boot.tech.web.response;


import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author fangyunhe
 *
 * 2017年9月13日 下午2:34:21
 */
@Data
public class Response<T> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer code;      
	
    private String msg;
    
    private T data;

    public Response(T data, Integer code, String msg){
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
    
    /**
     * 返回成功结果
     * @param <T>
     * @return
     */
    public static <T> Response<T> successResponse() {
        return new Response<T>(null, ResponseMsg.STATUS001.getCode(), ResponseMsg.STATUS001.getMsg());
    }
    
    public static <T> Response<T> successResponse(T data) {
        return new Response<T>(data, ResponseMsg.STATUS001.getCode(), ResponseMsg.STATUS001.getMsg());
    }
    
    public static <T> Response<T> successResponse(String msg) {
        return new Response<T>(null, ResponseMsg.STATUS001.getCode(), msg);
    }
    
    public static <T> Response<T> successResponse(T data,String msg) {
        return new Response<T>(data, ResponseMsg.STATUS001.getCode(), msg);
    }
    
    /**
     * 返回失败结果
     * @param <T>
     * @return
     */
    public static <T> Response<T> failedResponse() {
        return new Response<T>(null, ResponseMsg.STATUS002.getCode(), ResponseMsg.STATUS002.getMsg());
    }
    
    public static <T> Response<T> failedResponse(T data) {
        return new Response<T>(data, ResponseMsg.STATUS002.getCode(), ResponseMsg.STATUS002.getMsg());
    }
    
    public static <T> Response<T> failedResponse(String msg) {
        return new Response<T>(null, ResponseMsg.STATUS002.getCode(), msg);
    }
    
    /**
     * 返回参数校验失败结果
     * @param data
     * @return
     */
    public static <T> Response<T> paramsCheckFailResponse() {
        return new Response<T>(null, ResponseMsg.STATUS003.getCode(), ResponseMsg.STATUS003.getMsg());
    }
    
    public static <T> Response<T> paramsCheckFailResponse(T data) {
        return new Response<T>(data, ResponseMsg.STATUS003.getCode(), ResponseMsg.STATUS003.getMsg());
    }
    
    public static <T> Response<T> paramsCheckFailResponse(String msg) {
        return new Response<T>(null, ResponseMsg.STATUS003.getCode(), msg);
    }
    
    /**
     * 返回权限失败结果
     * @param <T>
     * @return
     */
    public static <T> Response<T> AuthResponse() {
        return new Response<T>(null, ResponseMsg.STATUS004.getCode(), ResponseMsg.STATUS004.getMsg());
    }
    
    public static <T> Response<T> AuthResponse(T data) {
        return new Response<T>(data, ResponseMsg.STATUS004.getCode(), ResponseMsg.STATUS004.getMsg());
    }
    
    public static <T> Response<T> AuthResponse(String msg) {
        return new Response<T>(null, ResponseMsg.STATUS004.getCode(), msg);
    }
    
    public static <T> Response<T> AuthResponse(T data,String msg) {
        return new Response<T>(data, ResponseMsg.STATUS004.getCode(), msg);
    }
    
    /**
     * 内部错误
     * @return
     */
    public static <T> Response<T> interiorErrorResponse() {
        return new Response<T>(null, ResponseMsg.STATUS005.getCode(), ResponseMsg.STATUS005.getMsg());
    }
}
