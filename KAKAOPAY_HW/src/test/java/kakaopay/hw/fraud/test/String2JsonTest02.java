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

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		System.out.println(gson.toJson(event1));
		System.out.println(gson.toJson(event2));
		System.out.println(gson.toJson(event3));
		System.out.println(gson.toJson(event4));
		System.out.println(gson.toJson(event5));

		time1 = LocalDateTime.of(2017, 12, 4, 12, 30);
		event1 = new EventInfo(Date.from(time1.atZone(ZoneId.systemDefault()).toInstant()), 1002, "1002", EventType.OPENING, 0, "", 0, 0, "");

		time2 = LocalDateTime.of(2017, 12, 5, 12, 30);
		event2 = new EventInfo(Date.from(time2.atZone(ZoneId.systemDefault()).toInstant()), 1002, "1002", EventType.RECEIVED, 120000, "1002", 1002, 120000, "");

		time3 = LocalDateTime.of(2017, 12, 6, 12, 30);
		event3 = new EventInfo(Date.from(time3.atZone(ZoneId.systemDefault()).toInstant()), 1002, "1002", EventType.RECEIVED, 230000, "1002", 1002, 110000, "");

		time4 = LocalDateTime.of(2017, 12, 7, 12, 30);
		event4 = new EventInfo(Date.from(time4.atZone(ZoneId.systemDefault()).toInstant()), 1002, "1002", EventType.RECEIVED, 330000, "1003", 1003, 100000, "");

		time5 = LocalDateTime.of(2017, 12, 8, 12, 30);
		event5 = new EventInfo(Date.from(time5.atZone(ZoneId.systemDefault()).toInstant()), 1002, "1002", EventType.SEND, 230000, "1002", 1002, 100000, "");

		LocalDateTime time6 = LocalDateTime.of(2017, 12, 8, 17, 30);
		EventInfo event6 = new EventInfo(Date.from(time6.atZone(ZoneId.systemDefault()).toInstant()), 1002, "1002", EventType.RECEIVED, 330000, "1003", 1003, 100000, "");

		LocalDateTime time7 = LocalDateTime.of(2017, 12, 9, 12, 30);
		EventInfo event7 = new EventInfo(Date.from(time7.atZone(ZoneId.systemDefault()).toInstant()), 1002, "1002", EventType.RECEIVED, 430000, "1002", 1002, 100000, "");

		System.out.println(gson.toJson(event1));
		System.out.println(gson.toJson(event2));
		System.out.println(gson.toJson(event3));
		System.out.println(gson.toJson(event4));
		System.out.println(gson.toJson(event5));
		System.out.println(gson.toJson(event6));
		System.out.println(gson.toJson(event7));

		time1 = LocalDateTime.of(2017, 12, 4, 11, 20);
		event1 = new EventInfo(Date.from(time1.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 52000, "1002", 1002, 51000, "");

		time2 = LocalDateTime.of(2017, 12, 4, 11, 30);
		event2 = new EventInfo(Date.from(time2.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 102000, "1002", 1002, 50000, "");

		time3 = LocalDateTime.of(2017, 12, 4, 11, 50);
		event3 = new EventInfo(Date.from(time3.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 151000, "1003", 1003, 49000, "");

		time4 = LocalDateTime.of(2017, 12, 4, 12, 20);
		event4 = new EventInfo(Date.from(time4.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 202000, "1004", 1004, 51000, "");

		time5 = LocalDateTime.of(2017, 12, 4, 12, 30);
		event5 = new EventInfo(Date.from(time5.atZone(ZoneId.systemDefault()).toInstant()), 1001, "1001", EventType.RECEIVED, 253000, "1002", 1002, 51000, "");

		System.out.println(gson.toJson(event1));
		System.out.println(gson.toJson(event2));
		System.out.println(gson.toJson(event3));
		System.out.println(gson.toJson(event4));
		System.out.println(gson.toJson(event5));
	}

	@Test
	public void jsonToObject() throws IOException {
		System.out.println("JSON -> VO ");
		File path = new File("C:\\Users\\insoo\\source\\git\\KAKAOPAY_HW\\events.txt");

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
