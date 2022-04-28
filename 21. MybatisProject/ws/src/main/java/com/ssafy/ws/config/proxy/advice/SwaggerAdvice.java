package com.ssafy.ws.config.proxy.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import io.swagger.annotations.ApiOperation;

public class SwaggerAdvice implements MethodInterceptor {

	@Override
	@ApiOperation(value = "")
	public Object invoke(MethodInvocation invocation) throws Throwable {

		try {

			Object result = invocation.proceed();

			return result;
		} catch (Exception e) {

			throw e;
		}

	}
}
