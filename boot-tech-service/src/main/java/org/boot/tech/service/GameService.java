package org.boot.tech.service;

import java.util.Date;

import javax.annotation.Resource;

import org.boot.tech.core.model.Game;
import org.boot.tech.mapper.GameMapper;
import org.boot.tech.util.UUIDUtil;
import org.springframework.stereotype.Service;

@Service
public class GameService {
	@Resource
	GameMapper gameMapper;
	
	public int insert(){
		Game record = new Game();
		record.setId(UUIDUtil.getUUID());
		record.setCompany("网易");
		record.setCreateDate(new Date());
		record.setName("阴阳师");
		record.setType("IOS");
		return gameMapper.insert(record);
	}
	
	public Game selectByPrimaryKey(String id){
		return gameMapper.selectByPrimaryKey(id);
	}
}
