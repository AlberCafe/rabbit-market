package com.albercafe.rabbitmarket;

import com.albercafe.rabbitmarket.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(AppProperties.class)
public class RabbitmarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmarketApplication.class, args);
	}

}
