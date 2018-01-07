package kakaopay.hw.fraud.model;

/**
 * 발생하는 이벤트의 종류 -> [계좌개설, 송금, 충전, 받기, 지불]
 * @author insoo
 *
 */
public enum EventType {
	OPENING, 	// 계좌개설
	SEND, 		// 송금
	CHARGE, 	// 충전
	RECEIVED,	// 받기
	PAYMENT	// 지불
}
