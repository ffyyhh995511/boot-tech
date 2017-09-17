package org.boot.tech.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.boot.tech.core.model.Game;
import org.boot.tech.core.service.GameService;
import org.boot.tech.mapper.GameMapper;

@com.alibaba.dubbo.config.annotation.Service
public class GameServiceImpl implements GameService{
	
	@Resource
	GameMapper gameMapper;
	
	@Override
	public List<Game> remoteQueryAll() {
		return gameMapper.queryAll();
	}

}
