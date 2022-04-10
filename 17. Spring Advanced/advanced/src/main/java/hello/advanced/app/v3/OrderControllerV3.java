package hello.advanced.app.v3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.advanced.trace.template.TraceTemplate;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

	private final OrderServiceV3 orderService;
	private final TraceTemplate template;

	@GetMapping("/v3/request")
	public String request(String itemId) {

		return template.execute("OrderController.request()", () -> {
			orderService.orderItem(itemId);
			return "ok";
		});
	}
}