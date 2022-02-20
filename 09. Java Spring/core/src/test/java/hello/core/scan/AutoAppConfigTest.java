package hello.core.scan;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;

public class AutoAppConfigTest {

	@Test
	void basicScan() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
			AutoAppConfig.class);

		MemberService bean = ac.getBean(MemberService.class);
		assertThat(bean).isInstanceOf(MemberService.class);
	}
}
