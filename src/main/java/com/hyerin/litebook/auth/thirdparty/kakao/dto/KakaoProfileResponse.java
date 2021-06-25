package com.hyerin.litebook.auth.thirdparty.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoProfileResponse {

	private Long id;

	private KakaoAccount kakaoAccount;

	public String getId() {
		return id.toString();
	}

	public KakaoAccount getKakaoAccount() {
		return kakaoAccount;
	}

}
