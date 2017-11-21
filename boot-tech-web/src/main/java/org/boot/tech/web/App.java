package org.boot.tech.web;

import org.boot.tech.web.aop.AspectJAdvice;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(scanBasePackages={"org.boot.tech"})
@ServletComponentScan
@MapperScan("org.boot.tech.core.mapper")
public class App implements CommandLineRunner{
//	private static final Logger logger = LoggerFactory.getLogger(App.class);
    
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		log.info("撸起来");
	}
}
