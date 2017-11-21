package org.boot.tech.api.service;

import java.util.List;

import org.boot.tech.api.model.Game;

public interface GameService {
	
	List<Game> remoteQueryAll();
	
	int remoteSave(Game game);
}
