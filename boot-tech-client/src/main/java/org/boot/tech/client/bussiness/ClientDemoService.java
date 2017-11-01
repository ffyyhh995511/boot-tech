package org.boot.tech.client.bussiness;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.boot.tech.api.model.Game;
import org.boot.tech.api.service.GameService;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
/**
 * 模拟做一些业务
 * @author Administrator
 *
 */
@Service
public class ClientDemoService {
	@Reference
	GameService gameService;
	
	@PostConstruct
	public void doSomething() throws InterruptedException {
		while (true) {
			List<Game> remoteQueryAll = gameService.remoteQueryAll();
			System.out.println("list长度：" + remoteQueryAll.size());
			Game game = new Game();
			game.setCompany("ABC");
			game.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			game.setCreateDate(new Date());
			game.setType("123");
			game.setName("XXX");
			gameService.remoteSave(game);
			Thread.sleep(1000);
		}
	}
}
