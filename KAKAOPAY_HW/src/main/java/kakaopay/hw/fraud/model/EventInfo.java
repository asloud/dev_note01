package kakaopay.hw.fraud.model;

import java.util.Date;

/**
 * 사용자 이벤트 정보 저장
 * @author insoo
 *
 */
public class EventInfo {
	
	// 기본 정보
	private Date eventDate;			// 이벤트 발생 시간
	private long userId;				// 이벤트 발생 사용자 ID
	private String userAccountInfo;		// 계좌번호
	private EventType eventType;		// 발생한 이벤트 종류
	
	// 송금, 충전, 받기 정보
	private long balance;				// 송금, 받기 전 계좌의 잔액
	private String kakaoAccount;		// 송금, 받기의 카카오머니 계좌
	private long otherUserId;				// 송금, 받기 카카오머니 사용자 ID
	private long price;					// 송금, 충전, 받기의 금액
	
	private String bankAccountInfo;	// 타행 계좌번호

	public EventInfo() {}

	public EventInfo(Date eventDate, long userId, String userAccountInfo, EventType eventType, long balance,
			String kakaoAccount, long otherUserId, long price, String bankAccountInfo) {
		this.eventDate = eventDate;
		this.userId = userId;
		this.userAccountInfo = userAccountInfo;
		this.eventType = eventType;
		this.balance = balance;
		this.kakaoAccount = kakaoAccount;
		this.otherUserId = otherUserId;
		this.price = price;
		this.bankAccountInfo = bankAccountInfo;
	}

	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserAccountInfo() {
		return userAccountInfo;
	}
	public void setUserAccountInfo(String userAccountInfo) {
		this.userAccountInfo = userAccountInfo;
	}

	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}

	public String getKakaoAccount() {
		return kakaoAccount;
	}
	public void setKakaoAccount(String kakaoAccount) {
		this.kakaoAccount = kakaoAccount;
	}

	public long getOtherUserId() {
		return otherUserId;
	}
	public void setOtherUserId(long otherUserId) {
		this.otherUserId = otherUserId;
	}

	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}

	public String getBankAccountInfo() {
		return bankAccountInfo;
	}
	public void setBankAccountInfo(String bankAccountInfo) {
		this.bankAccountInfo = bankAccountInfo;
	}

}
