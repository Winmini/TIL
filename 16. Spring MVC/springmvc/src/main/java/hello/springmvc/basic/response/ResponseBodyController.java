package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ResponseBodyController {

	@ResponseBody
	@GetMapping("/response-body-string")
	public String responseBody(){
		return "ok";
	}

}
