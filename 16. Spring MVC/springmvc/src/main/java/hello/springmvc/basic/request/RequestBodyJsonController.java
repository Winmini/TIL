package hello.springmvc.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestBodyJsonController {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@PostMapping("/request-body-json-v1")
	public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		log.info("messageBody={}", messageBody);

		HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
		log.info("helloData={}", helloData);

		response.getWriter().write("ok");
	}

	@ResponseBody
	@PostMapping("/request-body-json-v2")
	public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
		log.info("messageBody={}", messageBody);

		HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
		log.info("helloData={}", helloData);

		return "ok";
	}

	@ResponseBody
	@PostMapping("/request-body-json-v3")
	public String requestBodyJsonV3(HelloData helloData) {
		log.info("helloData={}", helloData);
		return "ok";
	}


}
