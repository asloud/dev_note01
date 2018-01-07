package kakaopay.hw.fraud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 시작 페이지.
	 * JSP 페이지에서 더미 이벤트 데이터의 사용자ID 목록을 보여준다
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/")
	public String initPaget(HttpServletRequest request, ModelMap map) {
		logger.info("index.jsp");
		List<Long> userIdList = this.ruleEngineService.getUserIdList();

		logger.info("USERS : {}", userIdList);
		map.addAttribute("userIdList", userIdList);

		return "index";
	}

	/**
	 * 룰 확인 API.
	 * 요청으로 사용자ID를 전달 받으면, 더미 이벤트 데이터에서 이상 거래 여부를 확인하여 응답을 만들어 전달한다. 
	 * @param request
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value="/v1/fraud/{user_id}", method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo checkFraud(HttpServletRequest request, @PathVariable long user_id) {
		logger.info("/v1/fraud/{}", user_id);
		ResultInfo resultInfo = this.ruleEngineService.checkService(user_id);
		logger.info("Result : {}", resultInfo.toString());
		return resultInfo;
	}
}
