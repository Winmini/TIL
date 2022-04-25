package hello.proxy.advisor;

import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import hello.proxy.common.advice.TimeAdvice;
import lombok.extern.slf4j.Slf4j;

public class AdvisorTest {

	@Test
	void advisorTest1() {
		ServiceInterface target = new ServiceInterface();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
		proxyFactory.addAdvisor(advisor);

		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		proxy.logic();

	}

	@Test
	void springPointcut() {
		ServiceInterface target = new ServiceInterface();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("save");
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
		proxyFactory.addAdvisor(advisor);

		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		proxy.logic();
		proxy.save();
	}

	@Test
	void multiAdvisorTest() {
		ServiceInterface target = new ServiceInterface();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("save");
		DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
		DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());

		proxyFactory.addAdvisor(advisor2);
		proxyFactory.addAdvisor(advisor1);

		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		proxy.logic();
		proxy.save();
	}

	@Slf4j
	static class ServiceInterface {
		public void logic() {
			log.info("로직 실행");
		}

		public void save() {
			log.info("저장 실행");
		}
	}
}
