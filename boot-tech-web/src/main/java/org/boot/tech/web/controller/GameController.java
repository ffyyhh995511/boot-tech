/**
 * 
 */
package org.boot.tech.web.controller;


import javax.annotation.Resource;

import org.boot.tech.api.model.Game;
import org.boot.tech.core.service.GameService;
import org.boot.tech.web.response.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fangyunhe
 * 2017年9月10日下午3:41:24
 */
@RestController
@RequestMapping(value="/game")
public class GameController extends BaseController{
	
	@Resource
	GameService GameService;
	
	@RequestMapping(value="/insert",method=RequestMethod.GET)
	public Object insert(){
		try {
			int res = GameService.insert();
			if(res > 0){
				return Response.successResponse(res, "游戏保存成功");
			}
		} catch (Exception e) {
			logger.error("游戏保存失败",e);
		}
		return Response.failedResponse("游戏保存失败");
	}
	
	@RequestMapping(value="/select",method=RequestMethod.GET)
	public Object select(String id){
		try {
			Game selectByPrimaryKey = GameService.selectByPrimaryKey(id);
			return Response.successResponse(selectByPrimaryKey, "查询成功");
		} catch (Exception e) {
			logger.error("游戏查询参数"+getParameterMap());
			logger.error("游戏查询失败",e);
		}
		return Response.failedResponse("查询不成功");
	}
}
