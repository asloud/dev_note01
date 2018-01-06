package kakaopay.hw.fraud.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자의 Rule engine 결과 정보 저장
 * @author insoo
 *
 */
public class ResultInfo {

	private long user_id;		// 요청 변수 사용자ID
	private boolean is_fraud = false;	// 룰에 의한 이상 거래 여부 (true : 이상거래, false : 정상거래)
	private String rule;			// 해당되는 룰 이름 (여러개 일 경우는 여러개 표기(콤마로 구분))

	public ResultInfo() {}

	public ResultInfo(long user_id, boolean is_fraud, String rule) {
		this.user_id = user_id;
		this.is_fraud = is_fraud;
		this.rule = rule;
	}

	public ResultInfo(long user_id, List<String> ruleNames) {
		this.user_id = user_id;
		if(ruleNames != null && !ruleNames.isEmpty()) {
			this.rule = ruleNames.stream().map(e -> e.toString()).collect(Collectors.joining(","));
			this.is_fraud = true;
		} else {
			this.is_fraud = false;
			this.rule = "";
		}
	}

	public long getUser_id() {
		return user_id;
	}

	public boolean isIs_fraud() {
		return is_fraud;
	}

	public String getRule() {
		return rule;
	}

	@Override
	public String toString() {
		return "ResultInfo [user_id=" + user_id + ", is_fraud=" + is_fraud + ", rule=" + rule + "]";
	}
}
