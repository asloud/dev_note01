package kakaopay.hw.fraud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kakaopay.hw.fraud.model.ResultInfo;
import kakaopay.hw.fraud.service.RuleEngineService;

/**
 * 룰 엔진의 조건에 의해서 모니터링 대상인지 판별
 * @author insoo
 *
 */
@Controller
public class RestAPIController {

	@Autowired
	private RuleEngineService ruleEngineService;

	/**
	 * 시작 페이지
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/")
	public String initPaget(HttpServletRequest request, ModelMap map) {
		List<Long> userIdList = this.ruleEngineService.getUserIdList();

		map.addAttribute("userIdList", userIdList);

		return "index";
	}

	/**
	 * 룰 확인 API
	 * @param request
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value="/v1/fraud/{user_id}", method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo checkFraud(HttpServletRequest request, @PathVariable long user_id) {

		ResultInfo resultInfo = this.ruleEngineService.checkService(user_id);
		return resultInfo;
	}
}
