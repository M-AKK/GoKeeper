package com.gokeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class GoKeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoKeeperApplication.class, args);
	}
}
