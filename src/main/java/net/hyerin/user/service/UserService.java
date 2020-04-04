package net.hyerin.user.service;

import net.hyerin.user.domain.User;
import net.hyerin.user.dto.UserSigninDto;
import net.hyerin.user.dto.UserSignupDto;

public interface UserService {
    public void signup(UserSignupDto userSignupDto);
    public User signin(UserSigninDto userSigninDto);
}
