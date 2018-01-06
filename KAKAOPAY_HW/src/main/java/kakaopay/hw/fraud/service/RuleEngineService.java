package kakaopay.hw.fraud.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kakaopay.hw.fraud.config.RuleConfig;
import kakaopay.hw.fraud.engine.RuleEngine;
import kakaopay.hw.fraud.model.EventInfo;
import kakaopay.hw.fraud.model.ResultInfo;

/**
 * 사용자ID에 맞는 이벤트 데이터를 조회하여 룰 엔진으로 확인
 * @author insoo
 *
 */
@Service
public class RuleEngineService {

	@Autowired
	private RuleConfig ruleConfig;	// 룰, 이벤트 데이터 제공

	@Autowired
	private RuleEngine ruleEngine;	// 룰 엔진 클래스

	@PostConstruct
	public void init() {
		this.ruleEngine.setRuleList(this.ruleConfig.getRuleList());
	}

	/**
	 * 사용자ID에 해당하는 이벤트 데이터를 룰 엔진에서 룰에 걸리는지 확인 후 결과 반환
	 * @param userId 사용자ID
	 * @return 룰 확인 결과
	 */
	public ResultInfo checkService(long userId) {
		List<EventInfo> eventList = ruleConfig.getEventList(userId);
		Optional<ResultInfo> resultOptional = this.ruleEngine.process(userId, eventList);
		
		ResultInfo resultInfo = resultOptional.get();
		
		return resultInfo;
	}

	/**
	 * 더미 사용자ID 목록 반환
	 * @return 더미 사용자ID 목록
	 */
	public List<Long> getUserIdList() {
		List<Long> userIdList = this.ruleConfig.getUserIdList();
		return userIdList;
	}
	 
}
