package kakaopay.hw.fraud.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kakaopay.hw.fraud.model.EventInfo;
import kakaopay.hw.fraud.model.RuleInfo;

/**
 * 설정 담당 클래스
 * 룰 데이터 설정(파일 읽기), 이벤트 더미 데이터 설정(파일 읽기), 이벤트에 있는 사용자ID 목록 반환
 * @author insoo
 *
 */
@Component
public class RuleConfig {

	@Value("${rules.file}")
	private String ruleFilePath;		// 룰 파일 경로
	
	@Value("${events.file}")
	private String eventFilePath;	// 더미 이벤트 파일 경로

	@Value("${gson.dateformat}")
	private String gsonDateFormat;	// JSON의 날짜 시간 포맷

	private List<RuleInfo> ruleList;		// 룰 정보 저장
	private List<EventInfo> eventList;	// 더미 이벤트 데이터 저장

	private ReadWriteLock lock;	// read write lock

	/**
	 * 초기화 진행.
	 * 룰, 더미 이벤트 데이터를 파일에서 읽어 설정
	 * @throws IOException
	 */
	@PostConstruct
	public void init() throws IOException {
		Gson gson = new GsonBuilder().setDateFormat(gsonDateFormat).create();

		// 룰 데이터를 파일에서 로딩 후 저장 (JSON -> Object) 
		List<String> ruleLines = this.getLines(this.ruleFilePath);
		this.ruleList = ruleLines.stream().map(line -> gson.fromJson(line, RuleInfo.class)).collect(Collectors.toList());

		// 사용자 이벤트 더미 데이터 로딩
		List<String> eventLines = this.getLines(this.eventFilePath);
		this.eventList = eventLines.stream().map(line -> gson.fromJson(line, EventInfo.class)).collect(Collectors.toList());

		// read write lock
		this.lock = new ReentrantReadWriteLock();
	}

	/**
	 * 파일에서 데이터를 읽어 한 라인씩 List에 저장 후 반환
	 * @param path 파일의 위치
	 * @return 파일 내용
	 * @throws IOException
	 */
	List<String> getLines(String path) throws IOException {
		List<String> lines = new ArrayList<>();
		
		BufferedReader in = new BufferedReader(new FileReader(new File(path)));
		String line = null;
		while((line = in.readLine()) != null) {
			if(!"".equals(line.trim())) {
				lines.add(line);
			}
		}
		in.close();
		
		return lines;
	}

	/**
	 * 룰 정보 반환
	 * @return 룰 정보 목록
	 */
	public List<RuleInfo> getRuleList() {
		return ruleList;
	}

	/**
	 * 모든 이벤트 정보 반환
	 * @return 더미 이벤트 목록
	 */
	public List<EventInfo> getEventList() {
		return eventList;
	}

	/**
	 * 이벤트 사용자ID 목록 반환
	 * @return 중복이 제거된 사용자ID 목록
	 */
	public List<Long> getUserIdList() {
		List<Long> userIdList = this.eventList.stream().map(EventInfo::getUserId).distinct().collect(Collectors.toList());
		return userIdList;
	}

	/**
	 * 특정 사용자의 이벤트만 반환
	 * @param userId 이벤트 조회 대상 사용자ID
	 * @return 사용자ID의 이벤트 목록
	 */
	public List<EventInfo> getEventList(long userId) {
		List<EventInfo> userEventList = this.eventList.stream().filter(event -> event.getUserId() == userId).collect(Collectors.toList());
		return userEventList;
	}

	/*
	 * 아래 두 메소드는 사용하지 않지만.. 작성
	 */
	
	// 룰 하나 추가
	public void addRule(RuleInfo ruleInfo) {
		this.lock.writeLock().lock(); // 쓰기 lock
		try {
			this.addRule(ruleInfo);
		} finally {
			this.lock.writeLock().unlock();	// 쓰기 unlock
		}
	}

	// 룰 목록 추가
	public void addRule(List<RuleInfo> ruleList) {
		this.lock.writeLock().lock(); // 쓰기 lock
		try {
			this.ruleList.addAll(ruleList);
		} finally {
			this.lock.writeLock().unlock();	// 쓰기 unlock
		}
	}
}
