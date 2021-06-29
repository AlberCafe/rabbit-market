package com.albercafe.rabbitmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RabbitmarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmarketApplication.class, args);
	}

}
