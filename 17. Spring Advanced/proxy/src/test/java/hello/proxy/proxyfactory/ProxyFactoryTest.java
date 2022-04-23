package hello.proxy.proxyfactory;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.common.advice.TimeAdvice;

public class ProxyFactoryTest {

	@Test
	void interfaceProxy() throws InterruptedException {
		OrderRepositoryV1Impl target = new OrderRepositoryV1Impl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice());
		Object proxy = proxyFactory.getProxy();
		proxy.wait();
	}
}
