package kakaopay.hw.fraud.model;



/**
 * 룰 정보 저장. RuleInfo 데이터가 모여 룰셋이 된다.
 * 계좌 개설 여부에 따라 엔진에서의 처리에 차이가 있다. (이벤트 시작 시간 설정의 차이)
 * 많은 생각 끝에 룰은 2가지의 종류가 있을 듯..
 * 		1. 계좌 개설 -> 충전/받기 || 송금/지불 -> 특정 기간 내 특정 행위 N회 이상 || 잔액 N원 이하
 * 		2. 기존 계좌 있음 -> 일정 시간 내 충전/받기/지불/송금 N회 이상 || 잔액 N원 이하
 * 계산이 복잡할 것 같아 비교는 이상/이하로 통일
 * 추후 JSON 데이터를 RuleInfo 객체로 변환하는 기능 추가 -> Rest API로 룰 추가 가능, (설정 파일로 설정도 물론 가능)
 * @author insoo
 *
 */
public class RuleInfo {

	private String ruleName;			// 룰 이름

	private boolean opening;			// 계좌 개설 여부
	private EventType eventType;		// 송금, 충전, 받기
	private long limitPrice;				// 제한금액
	private boolean checkBalance;	// 잔액 확인 여부
	private long lastBalance;			// 마지막 잔액

	private TimeUnit timeUnit;			// 시간 단위 - 시간, 일, 월 등
	private int timeValue;				// timeUnit 단위의 실제 값
	private int limitTimes;				// 제한 횟수 or 회수

	public RuleInfo() {}

	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public boolean isOpening() {
		return opening;
	}
	public void setOpening(boolean opening) {
		this.opening = opening;
	}

	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public long getLimitPrice() {
		return limitPrice;
	}
	public void setLimitPrice(long limitPrice) {
		this.limitPrice = limitPrice;
	}

	public boolean isCheckBalance() {
		return checkBalance;
	}
	public void setCheckBalance(boolean checkBalance) {
		this.checkBalance = checkBalance;
	}

	public long getLastBalance() {
		return lastBalance;
	}
	public void setLastBalance(long lastBalance) {
		this.lastBalance = lastBalance;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public int getTimeValue() {
		return timeValue;
	}
	public void setTimeValue(int timeValue) {
		this.timeValue = timeValue;
	}

	public int getLimitTimes() {
		return limitTimes;
	}
	public void setLimitTimes(int limitTimes) {
		this.limitTimes = limitTimes;
	}

}