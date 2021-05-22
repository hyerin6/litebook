package com.hyerin.litebook.auth.thirdparty.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoTokenResponse {

	private String tokenType;
	private String accessToken;
	private Integer expiresIn;
	private String refreshToken;
	private Integer refreshTokenExpiresIn;
	private String scope;

}