package hello.proxy.config.v4_postprocessor.postprocessor;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PackageLogTracePostProcessor implements BeanPostProcessor {

	private final String basePackage;
	private final Advisor advisor;

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("param beanName={}, bean={}", beanName, bean.getClass());

		// 프록시 적용 대상 여부 체크
		String packageName = bean.getClass().getPackageName();
		if (!packageName.startsWith(basePackage)){
			return bean;
		}

		ProxyFactory proxyFactory = new ProxyFactory(bean);
		proxyFactory.addAdvisor(advisor);
		return proxyFactory.getProxy();
	}
}
