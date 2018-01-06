package kakaopay.hw.fraud.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;

import kakaopay.hw.fraud.model.EventType;
import kakaopay.hw.fraud.model.RuleInfo;
import kakaopay.hw.fraud.model.TimeUnit;

/*
 * VO <-> String 테스트
 * enum이 잘 변경되나 확인
 */

public class String2JsonTest01 {

	@Test
	public void test() {
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

		Gson gson = new Gson();
		String jsonStr = gson.toJson(ruleA);

		System.out.println(jsonStr);

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

		System.out.println(gson.toJson(ruleB));

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
		
		System.out.println(gson.toJson(ruleC));
	}

	@Test
	public void testFileToObject() throws IOException {
		Gson gson = new Gson();

		BufferedReader in = new BufferedReader(new FileReader(new File("C:\\Users\\insoo\\source\\git\\KAKAOPAY_HW\\test01.json")));
		String line = in.readLine();
		in.close();

		System.out.println("before");
		System.out.println(line);

		System.out.println("after");
		RuleInfo rule = gson.fromJson(line, RuleInfo.class);
		System.out.println(rule);
		System.out.println(" ================== ");
	}
}
