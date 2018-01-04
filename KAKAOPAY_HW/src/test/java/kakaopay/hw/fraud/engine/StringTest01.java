package kakaopay.hw.fraud.engine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class StringTest01 {

	@Test
	public void test() {

//		this.rule = ruleList.stream().map(e -> e.toString()).collect(Collectors.joining(","));

		List<String> list = Arrays.asList("KAKAO", "PAY", "IS", "BEST");
		String line = list.stream().map(e -> e.toString()).collect(Collectors.joining(","));
		System.out.println(line);
		
		System.out.println(String.join(",", list));
	}

}
