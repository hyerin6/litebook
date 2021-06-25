package com.hyerin.litebook.user.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hyerin.litebook.user.domain.User;
import com.hyerin.litebook.user.repository.UserRepository;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public void signUp(User user) {
		userRepository.save(user);
	}

	public User getUser(String uid) throws Exception {
		return userRepository.findByUid(uid).orElseThrow(() -> new NotFoundException("회원가입이 필요한 사용자입니다."));
	}

}