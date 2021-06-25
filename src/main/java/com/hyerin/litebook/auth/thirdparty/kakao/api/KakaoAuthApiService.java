package com.hyerin.litebook.auth.thirdparty.kakao.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.hyerin.litebook.auth.thirdparty.kakao.dto.KakaoProfileResponse;
import com.hyerin.litebook.auth.thirdparty.kakao.dto.KakaoTokenResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@PropertySource("classpath:/auth.properties")
@RequiredArgsConstructor
@Component
public class KakaoAuthApiService {

	@Value("${kakao.access.token.url}")
	private String KAKAO_ACCESS_TOKEN_URL;

	@Value("${kakao.profile.url}")
	private String KAKAO_PROFILE_URL;

	private final WebClient apiWebClient;

	public KakaoTokenResponse getKakaoAccessToken(String code) {
		return apiWebClient.post()
			.uri(KAKAO_ACCESS_TOKEN_URL.concat(code))
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.retrieve()
			.bodyToMono(KakaoTokenResponse.class)
			.block();
	}

	public KakaoProfileResponse getKakaoProfile(String token) {
		return apiWebClient.get()
			.uri(KAKAO_PROFILE_URL)
			.headers(headers -> headers.setBearerAuth(token))
			.retrieve()
			.onStatus(status -> status.is4xxClientError(),
				response -> response.bodyToMono(KakaoErrorResponse.class)
					.map(body -> new IllegalArgumentException(body.getMsg())))
			.bodyToMono(KakaoProfileResponse.class)
			.block();
	}

	@Getter
	@AllArgsConstructor
	static class KakaoErrorResponse {
		private int code;
		private String msg;
	}

}
