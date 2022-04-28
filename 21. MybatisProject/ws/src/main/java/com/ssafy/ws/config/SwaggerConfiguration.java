package com.ssafy.ws.config;

import static springfox.documentation.builders.PathSelectors.*;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	private final String VERSION = "V1";
	private final String TITLE = "SSAFY GuestBook API " + VERSION;

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes()).produces(getProduceContentTypes())
			.apiInfo(apiInfo()).groupName(VERSION).select()
			.apis(RequestHandlerSelectors.basePackage("com.ssafy.ws.controller"))
			.paths(regex("/book/.*")).build()
			.useDefaultResponseMessages(false);
	}

	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}

	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(TITLE)
			.version("1.0").build();
	}
}
