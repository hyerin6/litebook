package com.hyerin.litebook.auth.thirdparty.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInRequest {

	private String uid;

	private String accessToken;

}
