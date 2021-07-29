package com.albercafe.rabbitmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket rabbitMarketAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getAPIInfo());
    }

    private ApiInfo getAPIInfo() {
        return new ApiInfoBuilder()
                .title("Rabbit Market API")
                .version("1.0")
                .description("API for Rabbit Market Application")
                .contact(new Contact("sdy", "https://github.com/dy-shin", "vel1024@naver.com"))
                .license("Apache License Version 2.0")
                .build();
    }
}
