package kakaopay.hw.fraud.engine;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import kakaopay.hw.fraud.model.TimeUnit;

public class RuleTest01 {

	@Test
	public void test() {

		/*
		 * 날짜 계산 테스트
		 */

		TimeUnit unit1 = TimeUnit.DAY;
		int timeValue = 4;

		// 둘 사이 3일 차이
		LocalDateTime startTime = LocalDateTime.of(2018, 1, 1, 21, 5, 22);
		LocalDateTime endTime = LocalDateTime.of(2018, 1, 4, 21, 5, 22);

		boolean result1 = checklimittime(startTime, endTime, unit1, timeValue);
		assertEquals("OK", result1, true);
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

		System.out.println("START TIME	:: " + startDate);
		System.out.println("END TIME	:: " + endDate);
		System.out.println("RESULT TIME	:: " + resultDate);
		return resultDate.isBefore(startDate);
	}
}
