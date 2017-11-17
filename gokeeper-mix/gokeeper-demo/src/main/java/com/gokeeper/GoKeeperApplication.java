package com.gokeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author akk
 */
@SpringBootApplication
@EnableScheduling
public class GoKeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoKeeperApplication.class, args);
	}
}
