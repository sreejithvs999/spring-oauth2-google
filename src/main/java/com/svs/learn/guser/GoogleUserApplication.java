package com.svs.learn.guser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoogleUserApplication {

	public static void main(String[] args) {
		
		System.setProperty("spring.devtools.restart.enabled", "false");  
		SpringApplication.run(GoogleUserApplication.class, args);
	}
}
