package kakaopay.hw.fraud.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kakaopay.hw.fraud.model.EventInfo;
import kakaopay.hw.fraud.model.EventType;

public class String2JsonTest02 {

	@Test
	public void test() {

		System.out.println("VO -> JSON");
		// VO -> JSON
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

		Gson gson = new Gson();
		System.out.println(gson.toJson(event1));
		System.out.println(gson.toJson(event2));
		System.out.println(gson.toJson(event3));
		System.out.println(gson.toJson(event4));
		System.out.println(gson.toJson(event5));
	}

	@Test
	public void jsonToObject() throws IOException {
		System.out.println("JSON -> VO ");
		File path = new File("C:\\Users\\insoo\\source\\git\\KAKAOPAY_HW\\events.json");
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line = null;
		EventInfo eventInfo = null;
		while((line = in.readLine()) != null) {
			eventInfo = gson.fromJson(line, EventInfo.class);
			System.out.println(eventInfo);
		}
		in.close();
	}
	
}
