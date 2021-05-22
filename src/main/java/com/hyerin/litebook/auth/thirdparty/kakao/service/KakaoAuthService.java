package com.hyerin.litebook.auth.thirdparty.kakao.service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hyerin.litebook.auth.thirdparty.dto.SignInRequest;
import com.hyerin.litebook.auth.thirdparty.dto.SignUpRequest;
import com.hyerin.litebook.auth.thirdparty.kakao.api.KakaoAuthApiService;
import com.hyerin.litebook.auth.thirdparty.kakao.dto.KakaoProfileResponse;
import com.hyerin.litebook.auth.thirdparty.kakao.dto.KakaoTokenResponse;
import com.hyerin.litebook.auth.thirdparty.service.SocialAuthService;
import com.hyerin.litebook.user.domain.User;
import com.hyerin.litebook.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoAuthService implements SocialAuthService<SignUpRequest, SignInRequest> {

	private static final String SESSION_LOGIN_KEY = "UID";
	private static final String BAD_REQUEST = "BAD REQUEST : ";

	private final KakaoAuthApiService kakaoAuthApiService;
	private final UserService userService;
	private final HttpSession httpSession;

	@Override
	@Transactional
	public void signUp(SignUpRequest request) {
		KakaoTokenResponse kakaoTokenResponse = kakaoAuthApiService.getKakaoAccessToken(request.getCode());
		KakaoProfileResponse kakaoProfile = kakaoAuthApiService.getKakaoProfile(kakaoTokenResponse.getAccessToken());

		User user = User.builder()
			.uid(kakaoProfile.getId())
			.email(kakaoProfile.getKakaoAccount().getEmail())
			.name(kakaoProfile.getKakaoAccount().getProfile().getNickname())
			.profile(kakaoProfile.getKakaoAccount().getProfile().getProfileImageUrl())
			.build();

		userService.signUp(user);
	}

	@Override
	public void signIn(SignInRequest request) throws Exception {
		KakaoProfileResponse kakaoProfile = kakaoAuthApiService.getKakaoProfile(request.getAccessToken());
		User user = userService.getUser(request.getUid());

		if (kakaoProfile.getId().equals(user.getUid())) {
			httpSession.setAttribute(SESSION_LOGIN_KEY, user.getUid());
			return;
		} else {
			throw new IllegalArgumentException(BAD_REQUEST.concat(request.getUid()));
		}

	}

}
