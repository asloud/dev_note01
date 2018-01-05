package kakaopay.hw.fraud.engine;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import kakaopay.hw.fraud.model.EventInfo;
import kakaopay.hw.fraud.model.EventType;
import kakaopay.hw.fraud.model.RuleInfo;
import kakaopay.hw.fraud.model.TimeUnit;

public class RuleEngine {

	private List<RuleInfo> ruleList = null;
	private List<EventInfo> eventList = null;

	public RuleEngine(List<RuleInfo> list) {
		this.ruleList = list;
	}

	// 룰셋을 만든다

	public void addRule(RuleInfo ruleInfo) {
		this.addRule(ruleInfo);
	}

	// before - 데이터 수신하여 정리한다

	public void process(List<EventInfo> eventList) {
		this.eventList = eventList;
	}

	public Optional<String> execute(RuleInfo rule) {
		// 룰 정보
		boolean isOpening = rule.isOpening();
		EventType eventType = rule.getEventType();
		long limitPrice = rule.getLimitPrice();
		TimeUnit timeUnit = rule.getTimeUnit();
		int timeValue = rule.getTimeValue();
		int limitTimes = rule.getLimitTimes();
		boolean checkBalance = rule.isCheckBalance();
		long lastBalance = rule.getLastBalance();

		LocalDateTime startDate = null;	// 룰 확인 시작 시간
		int eventCount = 0;		// 룰에 맞는 이벤트 발생 회수

		boolean resultBalance = false;
		boolean resultLimitTimes = false;
		boolean resultEventType = false;
		if(!checkBalance) {
			resultBalance = true;
		}
		if(limitTimes == 0) {
			resultLimitTimes = true;
		}

		// 룰 - 계좌계설이 true -> 계좌개설 이벤트 정보를 확인한다
		Optional<EventInfo> openingEvent = null;
		if(isOpening) {
			openingEvent = eventList.stream().filter(event -> event.getEventType() == EventType.OPENING).findAny();
			Date date = openingEvent.get().getEventDate();
			startDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		}

		// 계좌개설이 없다면 시작 시간 확인하는 방법 별도로 있어야 함

		// 회수와 제한기간
		LocalDateTime dateTime = null;
		for(EventInfo event : this.eventList) {
			// 1. 이벤트 타입이 맞는지 확인
			if(event.getEventType() == eventType && event.getPrice() >= limitPrice) {
				resultEventType = true;
				eventCount++;	// 회수 counting
			}

			if(checkBalance && 
					// 여기서 예외로 칠 이벤트 타입도 정할 수 있게 하던가..
					( event.getEventType() != EventType.OPENING && event.getEventType() != EventType.CHARGE ) && 
						( event.getBalance() <= lastBalance )) {
				System.out.println("EVENT Balance :: " + event.getBalance() + " || last balance :: " + lastBalance);
				resultBalance = true;
			}

			// 계좌 개설이 포함되지 않은 룰에서 시작 시간을 확인하는 경우
			if(!isOpening && eventCount == 0) {
				startDate = LocalDateTime.ofInstant(event.getEventDate().toInstant(), ZoneId.systemDefault());
			}

			if((limitTimes != 0) && eventCount >= limitTimes) {
				resultLimitTimes = true;
			}

			dateTime = LocalDateTime.ofInstant(event.getEventDate().toInstant(), ZoneId.systemDefault());
			if(this.checklimittime(startDate, dateTime, timeUnit, timeValue)) {
				if(resultBalance && resultEventType && resultLimitTimes) {
					break;
				}
			}

		}

		// 마지막 처리
		Optional<String> ruleName = null;
		if(resultBalance && resultEventType && resultLimitTimes) {
			ruleName = Optional.of(rule.getRuleName());
		} else {
			ruleName = Optional.empty();
		}
		return ruleName; // 룰 이름 반환
	}

	// after - 결과를 만든다
	public void after() {
		// 이벤트 목록을 비운다
		this.eventList.clear();
	}

	// true : 시간 over, false : 아직 더 남았음
	boolean checklimittime(LocalDateTime startDate, LocalDateTime endDate, TimeUnit timeUnit, int timevalue) {
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
