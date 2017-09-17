package org.boot.tech.core.service;

import java.util.List;

import org.boot.tech.core.model.Game;

public interface GameService {
	
	List<Game> remoteQueryAll();
}
