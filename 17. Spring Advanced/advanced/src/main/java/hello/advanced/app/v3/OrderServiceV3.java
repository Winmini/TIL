package hello.advanced.app.v3;

import org.springframework.stereotype.Service;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.TraceTemplate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {
	private final OrderRepositoryV3 orderRepository;
	private final TraceTemplate template;


	public void orderItem(String itemId) {
		template.execute("OrderController.request()", () -> {
			orderRepository.save(itemId);
			return null;
		});
	}
}