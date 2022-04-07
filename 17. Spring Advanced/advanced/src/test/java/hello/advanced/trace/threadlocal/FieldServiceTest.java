package hello.advanced.trace.threadlocal;

import static java.lang.Thread.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldServiceTest {

	private final FieldService fieldService= new FieldService();

	@Test
	public void field() throws Exception{
		log.info("main start");

		Runnable userA = () -> fieldService.logic("userA");
		Runnable userB = () -> fieldService.logic("userB");

		Thread threadA = new Thread(userA);
		threadA.setName("thread-A");
		Thread threadB = new Thread(userB);
		threadB.setName("thread-B");

		threadA.start();
		sleep(100);
		threadB.start();
		sleep(2000);
	}
}
