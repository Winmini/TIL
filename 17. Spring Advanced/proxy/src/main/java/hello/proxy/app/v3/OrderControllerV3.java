package hello.proxy.app.v3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.proxy.app.v2.OrderServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderControllerV3 {

	private final OrderServiceV3 orderService;

	@GetMapping("/v3/request")
	public String request(String itemId) {
		orderService.orderItem(itemId);
		return "ok";
	}

	@GetMapping("/v3/no-log")
	public String noLog() {
		return "ok";
	}
}
