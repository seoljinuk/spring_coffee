package com.coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoffeeApplication {
	// http://localhost:9000
	public static void main(String[] args) {
		SpringApplication.run(CoffeeApplication.class, args);
	}

}
