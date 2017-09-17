/**
 * 
 */
package org.boot.tech.web.controller;


import javax.annotation.Resource;

import org.boot.tech.core.model.Game;
import org.boot.tech.service.GameService;
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
				return responseSuccess("游戏保存成功+", res);
			}
		} catch (Exception e) {
			logger.error("游戏保存失败",e);
		}
		return responseFail("游戏保存失败");
	}
	
	@RequestMapping(value="/select",method=RequestMethod.GET)
	public Object select(String id){
		try {
			Game selectByPrimaryKey = GameService.selectByPrimaryKey(id);
			return responseSuccess("查询保存成功+", selectByPrimaryKey);
		} catch (Exception e) {
			logger.error("游戏查询参数"+getParameterMap());
			logger.error("游戏查询失败",e);
		}
		return responseFail("游戏查询失败");
	}
}