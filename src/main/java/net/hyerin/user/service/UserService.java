package net.hyerin.user.service;

import net.hyerin.user.domain.User;
import net.hyerin.user.dto.UserSigninDto;
import net.hyerin.user.dto.UserSignupDto;

// 인터페이스에 대한 설명 -> https://hyerin6.github.io/2020-04-05/0405/
public interface UserService {
    public void signup(UserSignupDto userSignupDto);
    public User signin(UserSigninDto userSigninDto);
}
