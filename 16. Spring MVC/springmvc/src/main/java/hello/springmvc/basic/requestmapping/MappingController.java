package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MappingController {

	@GetMapping("/hello-basic")
	public String helloBasic() {
		log.info("helloBasic");
		return "ok";
	}

	@GetMapping("/mapping/{userId}")
	public String mappingPath(@PathVariable("userId") String data) {
		log.info("mappingPath userId={}", data);
		return "ok";
	}

	@GetMapping("/mapping/users/{userId}/orders/{orderId}")
	public String mappingPath(@PathVariable String userId, @PathVariable String orderId) {
		log.info("mappingPath userId={}, orderId={}", userId, orderId);
		return "ok";
	}
}
