package kakaopay.hw.fraud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestController {

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "Rest Test";
	}
}
