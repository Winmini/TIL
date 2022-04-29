package hello.proxy.config.v6_aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.config.v6_aop.aspect.LogTraceAspect;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
public class AopConfig {

	@Bean
	public LogTraceAspect logTraceAspect(LogTrace logTrace) {
		return new LogTraceAspect(logTrace);
	}
}
