package hello.core.beanfind;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;

class ApplicationContextInfoTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

	@Test
	@DisplayName("모든 빈 출력하기")
	void findAllBean(){
		Arrays.stream(ac.getBeanDefinitionNames())
			.map(i -> ac.getBean(i))
			.forEach(System.out::println);

	}

	@Test
	@DisplayName("애플리케이션 빈 출력하기")
	void findAppBean(){
		// String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		// for (String beanDefinitionName : beanDefinitionNames) {
		// 	BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
		// 	if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
		// 		Object bean = ac.getBean(beanDefinitionName);
		// 		System.out.println("name = " + beanDefinitionName +  "Object = " + bean);
		// 	}
		// }
		Arrays.stream(ac.getBeanDefinitionNames())
			.filter(i -> ac.getBeanDefinition(i).getRole() == BeanDefinition.ROLE_APPLICATION)
			.map(i -> ac.getBean(i))
			.forEach(System.out::println);

	}

}
