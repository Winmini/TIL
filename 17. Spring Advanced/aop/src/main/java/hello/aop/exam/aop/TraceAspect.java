package hello.aop.exam.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class TraceAspect {

	@Around("@annotation(hello.aop.exam.annotation.Trace)")
	public void logTrace(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		log.info("[Trace] {} args={}", joinPoint.getSignature(), args);

	}
}
