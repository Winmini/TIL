package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LogTestController {
//	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping("/log-test")
	public String logTest() {
		String name = "Spring";

		System.out.println("name = " + name);
		log.trace("trace log = " + name);
		log.trace("trace log = {}", name);
		log.debug("debug log = {}", name); // 디버그 할 때 보는 것
		log.info("info log = {}", name); // 중요한 비즈니스 정보
		log.warn("warn log = {}", name); // 경고
		log.error("error log = {}", name); // 에러

		return "ok";
	}
}
