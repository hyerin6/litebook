package com.hyerin.litebook.auth.controller;

import static com.hyerin.litebook.common.dto.ResponseEntityConstants.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyerin.litebook.auth.thirdparty.dto.SignInRequest;
import com.hyerin.litebook.auth.thirdparty.dto.SignUpRequest;
import com.hyerin.litebook.auth.thirdparty.service.SocialAuthService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

	private final SocialAuthService kakaoAuthService;

	@GetMapping("/third-parties/kakao/sign-up")
	public ResponseEntity<Void> kakaoSignUp(@RequestParam("code") String code) {
		kakaoAuthService.signUp(new SignUpRequest(code));
		return CREATED;
	}

	@PostMapping("/third-parties/kakao/sign-in")
	public ResponseEntity<Void> kakaoSignIn(@RequestBody SignInRequest request) {
		try {
			kakaoAuthService.signIn(request);
		} catch (Exception e) {
			return BAD_REQUEST;
		}
		
		return OK;
	}

}
