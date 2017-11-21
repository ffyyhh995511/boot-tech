package org.boot.tech.core.mapper;

import java.util.List;

import org.boot.tech.api.model.Game;

public interface GameMapper {
    int deleteByPrimaryKey(String id);

    int insert(Game record);

    int insertSelective(Game record);

    Game selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Game record);

    int updateByPrimaryKey(Game record);

	List<Game> queryAll();
}