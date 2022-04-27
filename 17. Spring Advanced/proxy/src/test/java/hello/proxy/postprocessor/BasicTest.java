package hello.proxy.postprocessor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

public class BasicTest {

	@Test
	void basicConfig() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
			BasicConfig.class); // 스프링 컨테이너

		B beanB = applicationContext.getBean("beanA", B.class);
		beanB.helloB();
	}

	@Slf4j
	@Configuration
	static class BasicConfig {
		@Bean(name = "beanA")
		public A a() {
			return new A();
		}

		@Bean
		public AToBPostProcessor aToBPostProcessor() {
			return new AToBPostProcessor();
		}
	}

	@Slf4j
	static class A {
		public void helloA() {
			log.info("hello A");
		}
	}

	@Slf4j
	static class B {
		public void helloB() {
			log.info("hello B");
		}
	}

	@Slf4j
	static class AToBPostProcessor implements BeanPostProcessor {
		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
			log.info("bean={}", bean);
			log.info("beanName={}", beanName);
			if (bean instanceof A) {
				return new B();
			}
			return bean;
		}
	}
}
