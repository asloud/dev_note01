package kakaopay.hw.fraud.engine;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import kakaopay.hw.fraud.model.EventInfo;
import kakaopay.hw.fraud.model.EventType;
import kakaopay.hw.fraud.model.RuleInfo;
import kakaopay.hw.fraud.model.TimeUnit;

public class RuleEngine {

	private List<RuleInfo> ruleList = new ArrayList<>();
	private List<EventInfo> eventList = new ArrayList<>();

	// 룰셋을 만든다

	// before - 데이터 수신하여 정리한다

	// 엔진 - 룰에 맞는지 확인한다
	public void process() {

	}

	public void execute(RuleInfo rule) {
		// 룰 정보
		boolean isOpening = rule.isOpening();
		EventType eventType = rule.getEventType();
		long limitPrice = rule.getLimitPrice();
		TimeUnit timeUnit = rule.getTimeUnit();
		int timeValue = rule.getTimeValue();
		int limitTimes = rule.getLimitTimes();
		boolean checkBalance = rule.isCheckBalance();

		LocalDateTime startDate = null;	// 룰 확인 시작 시간
		int eventCount = 0;		// 룰에 맞는 이벤트 발생 회수

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
		long lastBalance = 0;	// 마지막 잔액
		for(EventInfo event : this.eventList) {
			// 1. 이벤트 타입이 맞는지 확인
			if(event.getEventType() != eventType) {
				continue;
			}

			// 잔액 확인 안하고, 이벤트 금액 확인
			if(!checkBalance && (event.getPrice() >= limitPrice)) {
				continue;
			}
			
			// 계좌 개설이 포함되지 않은 룰에서 시작 시간을 확인하는 경우
			if(!isOpening && eventCount == 0) {
				startDate = LocalDateTime.ofInstant(event.getEventDate().toInstant(), ZoneId.systemDefault());
			}

			eventCount++;	// 회수 counting

			// 제한시간 계산 - 넘어가면 break가 아님 - 또 이 부분에서 버그 발생할 듯
			// 이벤트 회수가 넘어가면 break 해야 함 - 룰 설정을 잘 해야 한다는 숙제가 있음
			dateTime = LocalDateTime.ofInstant(event.getEventDate().toInstant(), ZoneId.systemDefault());
			if((eventCount >= limitTimes) && this.checklimittime(startDate, dateTime, timeUnit, timeValue)) {
				lastBalance = event.getBalance();
				break;
			}
		}
		
		// 잔액 확인이 필요한 경우
		// 잔액이 제한금액 보다 작은 경우
		if(checkBalance && (lastBalance <= limitPrice)) {
			
		}
		
		// 제한회수 확인
		if(limitTimes >= eventCount) {
			
		}

		// 마지막 처리
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
