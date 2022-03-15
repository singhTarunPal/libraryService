package com.bits.library.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@EnableAutoConfiguration
public class SwaggerConfig {

	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("v1")
				.apiInfo(apiInfo()).select()
				.paths(regex("/library/.*")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SpringBoot Library Service API with Swagger")
				.description("SpringBoot REST API with Swagger")
				.contact(new Contact("Tarun Pal Singh", null, "singhtarunpal@gmail.com"))
				.build();
	}
}