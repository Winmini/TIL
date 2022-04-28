package com.ssafy.ws.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ssafy.ws.config.proxy.advice.SwaggerAdvice;

@Configuration
public class AutoProxyConfig {

	@Bean
	public Advisor advisor() {
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedNames("*WithSwagger");
		SwaggerAdvice advice = new SwaggerAdvice();
		return new DefaultPointcutAdvisor(pointcut, advice);
	}
}