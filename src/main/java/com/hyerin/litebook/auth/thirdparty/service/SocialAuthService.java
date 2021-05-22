package com.hyerin.litebook.auth.thirdparty.service;

import com.hyerin.litebook.auth.thirdparty.dto.SignInRequest;
import com.hyerin.litebook.auth.thirdparty.dto.SignUpRequest;

public interface SocialAuthService<SIGNUP extends SignUpRequest, SIGNIN extends SignInRequest> {

	void signUp(SIGNUP request);

	void signIn(SIGNIN request) throws Exception;

}
