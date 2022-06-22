package hello.aop.pointcut;

import java.lang.reflect.Method;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutionTest {

	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	Method helloMethod;


	@BeforeEach
	void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	@Test
	void printMethod() {
		log.info("helloMethod={}", helloMethod);
	}

	@Test
	void exactMatch() {
		pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void allMatch() {
		pointcut.setExpression("execution(* *(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatch() {
		pointcut.setExpression("execution(* hello(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatch2() {
		pointcut.setExpression("execution(* hel*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatch3() {
		pointcut.setExpression("execution(* *el*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageExactMatch() {
		pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageExactMatch2() {
		pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageSubMatch() {
		pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageSubMatch2() {
		pointcut.setExpression("execution(* hello.aop..*.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void typeSuperMatch() { // 부모 타입 매칭해도 자식이 매칭이 됨
		pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
}
