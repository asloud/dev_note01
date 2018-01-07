package kakaopay.hw.fraud.engine;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kakaopay.hw.fraud.model.EventInfo;
import kakaopay.hw.fraud.model.EventType;
import kakaopay.hw.fraud.model.ResultInfo;
import kakaopay.hw.fraud.model.RuleInfo;
import kakaopay.hw.fraud.model.TimeUnit;

/**
 * 룰 엔진 클래스.
 * 사용자ID 전달 받아 이벤트 데이터에서 해당 사용자 이벤트가 룰에 필터링되는지 확인하여 결과 반환하는 기능 제공
 * @author insoo
 *
 */
@Component
public class RuleEngine {

	private List<RuleInfo> ruleList = null;		// 룰 정보 저장
	private List<EventInfo> eventList = null;	// 이벤트 목록 저장

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 생성자
	public RuleEngine() {}

	/**
	 * 룰 정보 목록 설정
	 * @param ruleList 룰 정보 목록
	 */
	public void setRuleList(List<RuleInfo> ruleList) {
		this.ruleList = ruleList;
	}

	/**
	 * 룰 엔진 처리 메소드. 엔진의 전체적인 동작 수행.
	 * 룰이 있는지 확인 후 룰 하나씩 이벤트 목록 처리한다.
	 * 결과 정리 후 반환
	 * @param eventList 룰 엔진 처리 대상 이벤트 목록
	 * @return 결과
	 */
	public Optional<ResultInfo> process(long userId, List<EventInfo> eventList) {
		logger.info("RuleEngine - USER ID : {}", userId);
		this.eventList = eventList;
		Optional<ResultInfo> resultOptional = null;	// 결과
		List<String> ruleNames = new ArrayList<>();	// 필터링되는 룰 이름 저장

		// 혹시나 룰이 없으면 - 처리하지 않음
		if(this.ruleList ==null || this.ruleList.isEmpty()) {
			return Optional.empty();
		}

		Optional<String> ruleName = null;
		// 룰 하나씩 처리
		for(RuleInfo ruleInfo : this.ruleList) {
			ruleName = this.execute(ruleInfo);
			if(ruleName.isPresent()) {
				ruleNames.add(ruleName.get());
			}
		}

		// 결과 데이터 생성
		ResultInfo resultInfo = new ResultInfo(userId, ruleNames);
		resultOptional = Optional.of(resultInfo);

		this.eventList.clear();
		return resultOptional;
	}

	/**
	 * 룰 하나에 대한 이벤트 데이터를 처리
	 * @param rule 확인 대상 룰 정보
	 * @return 이벤트의 룰 확인 결과
	 */
	public Optional<String> execute(RuleInfo rule) {
		// 룰 정보
		boolean isOpening = rule.isOpening();			// 계좌 개설인가?
		EventType eventType = rule.getEventType();	// 룰의 이벤트 종류
		long limitPrice = rule.getLimitPrice();				// 제한 금액
		TimeUnit timeUnit = rule.getTimeUnit();			// 시간 단위 - 시간, 일, 월
		int timeValue = rule.getTimeValue();				// 시간 값
		int limitTimes = rule.getLimitTimes();				// 제한 횟수
		boolean checkBalance = rule.isCheckBalance(); // 잔액 확인 여부
		long lastBalance = rule.getLastBalance();		// 마지막 잔액

		LocalDateTime startDate = null;	// 룰 확인 시작 시간
		int eventCount = 0;					// 룰에 맞는 이벤트 발생 회수

		boolean resultBalance = false;		// 결과 - 잔액이 룰을 만족하는가?
		boolean resultLimitTimes = false;	// 결과 - 제한횟수를 만족하는가?
		boolean resultEventType = false;		// 결과 - 이벤트 종류를 만족하는가?
		if(!checkBalance) {
			// 잔액 확인을 안 하는 경우 - 결과를 true로 설정
			resultBalance = true;
		}
		if(limitTimes == 0) {
			// 제한 횟수를 확인 안 하는 경우 - 결과를 true로 설정
			resultLimitTimes = true;
		}
		
		// 룰 - 계좌계설이 true -> 계좌개설 이벤트 정보를 확인한다
		// 룰이 계좌개설인 경우의 startDate 값을 설정하는 부분
		Optional<EventInfo> openingEvent = null;
		if(isOpening) {
			openingEvent = eventList.stream().filter(event -> event.getEventType() == EventType.OPENING).findAny();
			Date date = null;
			if(openingEvent.isPresent()) {
				date = openingEvent.get().getEventDate();
			} else {
				// 룰은 계좌개설인데, 데이터가 계좌개설이 아닌 경우
				openingEvent = eventList.stream().findFirst();
				date = openingEvent.get().getEventDate();
			}
			// 룰의 데이터 시작 시간 설정
			startDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		}

		// 회수와 제한기간
		LocalDateTime dateTime = null;
		for(EventInfo event : this.eventList) {
			// 1. 이벤트 타입이 맞는지 확인
			if(event.getEventType() == eventType && event.getPrice() >= limitPrice) {
				resultEventType = true;
				eventCount++;	// 회수 counting
			} else if(!rule.isContinousCheck()) {
				continue;
			}

			if(checkBalance && 
					// 여기서 예외로 칠 이벤트 타입도 정할 수 있게 하던가..
					( event.getEventType() != EventType.OPENING && event.getEventType() != EventType.CHARGE ) && 
					( event.getBalance() <= lastBalance )) {
				resultBalance = true;
			}

			// 계좌 개설이 포함되지 않은 룰에서 시작 시간을 확인하는 경우
			if(!isOpening && eventCount == 1) {
				startDate = LocalDateTime.ofInstant(event.getEventDate().toInstant(), ZoneId.systemDefault());
			}

			if((limitTimes != 0) && eventCount >= limitTimes) {
				resultLimitTimes = true;
			}

			// 제한 시간을 만족하는지 확인
			dateTime = LocalDateTime.ofInstant(event.getEventDate().toInstant(), ZoneId.systemDefault());
			if(this.checkLimitTime(startDate, dateTime, timeUnit, timeValue)) {

				// 이벤트 데이터 조회 시 모든 조건을 만족하면 이제 그만 조회한다
				if(resultBalance && resultEventType && resultLimitTimes) {
					break;
				}
			}

		}

		// 마지막 처리 - 룰을 만족하지 못 하고 for 루프가 끝날수도 있어서..
		Optional<String> ruleName = null;
		if(resultBalance && resultEventType && resultLimitTimes) {
			// 룰을 만족한 경우
			ruleName = Optional.of(rule.getRuleName());
		} else {
			// 룰을 만족하지 못한 경우 - 빈 값
			ruleName = Optional.empty();
		}
		return ruleName; // 룰 이름 반환
	}

	/**
	 * 이벤트의 시간이 룰의 제한 시간을 지났는지 확인
	 * @param startDate 시작 시간
	 * @param endDate 마지막 시간
	 * @param timeUnit 시간 단위
	 * @param timevalue 시간 값
	 * @return true : 시간 over, false : 아직 더 남았음
	 */
	boolean checkLimitTime(LocalDateTime startDate, LocalDateTime endDate, TimeUnit timeUnit, int timevalue) {
		LocalDateTime resultDate = null;
		switch(timeUnit) {
		case HOUR:
			resultDate = endDate.minusHours(timevalue);
			break;
		case DAY:
			resultDate = endDate.minusDays(timevalue);
			break;
		case MONTH:
			resultDate = endDate.minusMonths(timevalue);
			break;
		}

		return resultDate.isBefore(startDate);
	}
}
