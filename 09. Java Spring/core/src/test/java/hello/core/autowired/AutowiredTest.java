package hello.core.autowired;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.member.Member;

public class AutowiredTest {

	@Test
	void autowiredOption() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
			TestBean.class);

	}

	static class TestBean {

		@Autowired(required = false)
		public void setNoBean(Member noBean) {
			System.out.println("noBean = " + noBean);
		}

		@Autowired
		public void setOptionalBean(Optional<Member> noBean) {
			System.out.println("noBean = " + noBean);
		}
	}
}
