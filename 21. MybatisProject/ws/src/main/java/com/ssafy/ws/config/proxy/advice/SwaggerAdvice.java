package com.ssafy.ws.config.proxy.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

public class SwaggerAdvice implements MethodInterceptor {

	@Override
	@ApiOperation(value = "")
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			return invocation.proceed();
		} catch (Exception e) {
			throw e;
		}

	}
}
