package org.boot.tech.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"org.boot.tech.client"})
public class ClientApp {
	private static final Logger logger = LoggerFactory.getLogger(ClientApp.class);
    
	public static void main(String[] args) {
		SpringApplication.run(ClientApp.class, args);
		logger.info("远程客户端撸起来");
	}
}
