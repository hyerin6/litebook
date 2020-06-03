package net.hyerin.user.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import net.hyerin.user.domain.User;
import net.hyerin.user.dto.UserModifyDto;
import net.hyerin.user.dto.UserSignupDto;

// 인터페이스에 대한 설명 -> https://hyerin6.github.io/2020-04-05/0405/
public interface UserService {
    public void signup(UserSignupDto userSignupDto) throws Exception;
    public void updateUserType(String email);
    public boolean hasErrors(UserSignupDto userSignupDto, BindingResult bindingResult);
    public User findById(Long id);
    public User findByEmail(String email);
    public User modifyProfile(UserModifyDto userModifyDto, Long userId) throws Exception;
    public void deleteUser(User user);
    public List<User> searchUsers(String name);
}
