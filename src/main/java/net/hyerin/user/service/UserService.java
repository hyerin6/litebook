package net.hyerin.user.service;

import net.hyerin.user.domain.User;
import net.hyerin.user.dto.UserSigninDto;
import net.hyerin.user.dto.UserSignupDto;
import org.springframework.validation.BindingResult;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

// 인터페이스에 대한 설명 -> https://hyerin6.github.io/2020-04-05/0405/
public interface UserService {
    public void signup(UserSignupDto userSignupDto) throws Exception;
    public User signin(UserSigninDto userSigninDto);
    public void updateUserType(String email);
    public boolean hasErrors(UserSignupDto userSignupDto, BindingResult bindingResult);
}
