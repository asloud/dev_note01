package kakaopay.hw.fraud.engine;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import kakaopay.hw.fraud.model.EventInfo;
import kakaopay.hw.fraud.model.EventType;
import kakaopay.hw.fraud.model.RuleInfo;
import kakaopay.hw.fraud.model.TimeUnit;

public class EngineTest01 {

	// 이벤트 목록 저장
	private List<EventInfo> eventList = new ArrayList<>();

	// 룰 목록 저장
	private List<RuleInfo> ruleList = null;

	@Before
	public void before() {
		// 룰 생성
		this.ruleList = this.makeRules();

		// 이벤트 생성 - eventA - ruleA
		this.eventList.addAll(this.makeEventA());
		// 이벤트 생성 - eventB - ruleB
		this.eventList.addAll(this.makeEventB());
		// 이벤트 생성 - eventC - ruleC
		this.eventList.addAll(this.makeEventC());
	}

	// 룰 생성
	List<RuleInfo> makeRules() {
		List<RuleInfo> ruleList = new ArrayList<>();

		// 카카오머니 서비스 계좌 개설을 하고 1시간 이내, 20만원 충전 후 잔액이 1000원 이하가 되는 경우
		RuleInfo ruleA = new RuleInfo();
		ruleA.setRuleName("ruleA");	// 룰 이름
		ruleA.setOpening(true);		// 계좌개설
		ruleA.setTimeUnit(TimeUnit.HOUR);
		ruleA.setTimeValue(1);			// 1시간 이내
		ruleA.setEventType(EventType.CHARGE);	// 충전
		ruleA.setLimitPrice(200000);	// 20 만원 => 20만원 충전
		ruleA.setCheckBalance(true);	// 잔액 확인
		ruleA.setLastBalance(1000);
		ruleA.setLimitTimes(0);

		ruleList.add(ruleA);

		//  카카오머니 서비스 계좌 개설을 하고 7일 이내, 카카오머니 받기로 10만원 이상 금액을 5회 이상 하는 경우
		RuleInfo ruleB = new RuleInfo();
		ruleB.setRuleName("ruleB");
		ruleB.setOpening(true);
		ruleB.setTimeUnit(TimeUnit.DAY);
		ruleB.setTimeValue(7);
		ruleB.setEventType(EventType.RECEIVED);
		ruleB.setLimitPrice(100000);
		ruleB.setLimitTimes(5);
		ruleB.setCheckBalance(false);

		ruleList.add(ruleB);

		// 2시간 이내, 카카오머니 받기로 5만원 이상 금액을 3회 이상 하는 경우
		RuleInfo ruleC = new RuleInfo();
		ruleC.setRuleName("ruleC");
		ruleC.setOpening(false);
		ruleC.setTimeUnit(TimeUnit.HOUR);
		ruleC.setTimeValue(2);
		ruleC.setEventType(EventType.RECEIVED);
		ruleC.setLimitPrice(50000);
		ruleC.setLimitTimes(3);
		ruleC.setCheckBalance(false);

		ruleList.add(ruleC);

		return ruleList;
	}

	// 이벤트A 목록 생성
	List<EventInfo> makeEventA() {
		// EventInfo(Date eventDate, long userId, String userAccountInfo, EventType eventType, long balance, 
		//				String kakaoAccount, long otherUserId, long price, String bankAccountInfo) {
		List<EventInfo> eventList = new ArrayList<>();

		LocalDateTime time1 = LocalDateTime.of(2017, 12, 31, 12, 12);
		EventInfo event1 = new EventInfo(Date.from(time1.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.OPENING, 0, "", -1, 0, "");	// 계좌개설

		LocalDateTime time2 = LocalDateTime.of(2017, 12, 31, 12, 15);
		EventInfo event2 = new EventInfo(Date.from(time2.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.CHARGE, 200000, "", 0, 200000, "");	// 충전

		LocalDateTime time3 = LocalDateTime.of(2017, 12, 31, 12, 22);
		EventInfo event3 = new EventInfo(Date.from(time3.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.SEND, 100000, "1002", 1002, 100000, "");	// 송금

		LocalDateTime time4 = LocalDateTime.of(2017, 12, 31, 12, 34);
		EventInfo event4 = new EventInfo(Date.from(time4.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.SEND, 10000, "1003", 1003, 90000, "");	// 송금

		LocalDateTime time5 = LocalDateTime.of(2017, 12, 31, 12, 54);
		EventInfo event5 = new EventInfo(Date.from(time5.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.SEND, 500, "1002", 1002, 9500, "");	// 송금

		eventList.add(event1);
		eventList.add(event2);
		eventList.add(event3);
		eventList.add(event4);
		eventList.add(event5);

		return eventList;
	}

	// 이벤트B 목록 생성
	List<EventInfo> makeEventB() {
		// EventInfo(Date eventDate, long userId, String userAccountInfo, EventType eventType, long balance, 
		//					String kakaoAccount, long otherUserId, long price, String bankAccountInfo) {
		List<EventInfo> eventList = new ArrayList<>();

		// EventInfo(Date eventDate, long userId, String userAccountInfo, EventType eventType, long balance, 
		//					String kakaoAccount, long otherUserId, long price, String bankAccountInfo) {
		LocalDateTime time1 = LocalDateTime.of(2017, 12, 4, 12, 30);
		EventInfo event1 = new EventInfo(Date.from(time1.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.OPENING, 0, "", 0, 0, "");

		LocalDateTime time2 = LocalDateTime.of(2017, 12, 5, 12, 30);
		EventInfo event2 = new EventInfo(Date.from(time2.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 120000, "1002", 1002, 120000, "");

		LocalDateTime time3 = LocalDateTime.of(2017, 12, 6, 12, 30);
		EventInfo event3 = new EventInfo(Date.from(time3.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 230000, "1002", 1002, 110000, "");

		LocalDateTime time4 = LocalDateTime.of(2017, 12, 7, 12, 30);
		EventInfo event4 = new EventInfo(Date.from(time4.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 330000, "1003", 1003, 100000, "");

		LocalDateTime time5 = LocalDateTime.of(2017, 12, 8, 12, 30);
		EventInfo event5 = new EventInfo(Date.from(time5.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.SEND, 230000, "1002", 1002, 100000, "");

		LocalDateTime time6 = LocalDateTime.of(2017, 12, 8, 17, 30);
		EventInfo event6 = new EventInfo(Date.from(time6.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 330000, "1003", 1003, 100000, "");

		LocalDateTime time7 = LocalDateTime.of(2017, 12, 9, 12, 30);
		EventInfo event7 = new EventInfo(Date.from(time7.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 430000, "1002", 1002, 100000, "");

		eventList.add(event1);
		eventList.add(event2);
		eventList.add(event3);
		eventList.add(event4);
		eventList.add(event5);
		eventList.add(event6);
		eventList.add(event7);

		return eventList;
	}

	List<EventInfo> makeEventC() {
		// EventInfo(Date eventDate, long userId, String userAccountInfo, EventType eventType, long balance, 
		//					String kakaoAccount, long otherUserId, long price, String bankAccountInfo) {
		List<EventInfo> eventList = new ArrayList<>();

		LocalDateTime time1 = LocalDateTime.of(2017, 12, 4, 12, 30);
		EventInfo event1 = new EventInfo(Date.from(time1.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 52000, "1002", 1002, 51000, "");

		LocalDateTime time2 = LocalDateTime.of(2017, 12, 4, 12, 30);
		EventInfo event2 = new EventInfo(Date.from(time2.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 102000, "1002", 1002, 50000, "");

		LocalDateTime time3 = LocalDateTime.of(2017, 12, 4, 12, 30);
		EventInfo event3 = new EventInfo(Date.from(time3.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 151000, "1003", 1003, 49000, "");

		LocalDateTime time4 = LocalDateTime.of(2017, 12, 4, 12, 30);
		EventInfo event4 = new EventInfo(Date.from(time4.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 202000, "1004", 1004, 51000, "");

		LocalDateTime time5 = LocalDateTime.of(2017, 12, 4, 12, 30);
		EventInfo event5 = new EventInfo(Date.from(time5.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 253000, "1002", 1002, 51000, "");

		eventList.add(event1);
		eventList.add(event2);
		eventList.add(event3);
		eventList.add(event4);
		eventList.add(event5);

		return eventList;
	}

	@Test
	public void test() {

		RuleEngine ruleEngine = new RuleEngine( this.ruleList );
		ruleEngine.process( this.eventList );
//		Optional<String> result = ruleEngine.execute( this.ruleList.get(0) );
//		Optional<String> result = ruleEngine.execute( this.ruleList.get(1) );
		Optional<String> result = ruleEngine.execute( this.ruleList.get(2) );

		System.out.println(result);
	}

}
