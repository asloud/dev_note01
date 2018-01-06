package kakaopay.hw.fraud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kakaopay.hw.fraud.service.RuleEngineService;

@Controller
public class RestController {

	@Autowired
	private RuleEngineService ruleEngineService;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "Rest Test";
	}

	@RequestMapping("/")
	public String initPaget(HttpServletRequest request, ModelMap map) {
		List<Long> userIdList = this.ruleEngineService.getUserIdList();
		
		map.addAttribute("userIdList", userIdList);
		
		return "index";
	}
}
