package net.hyerin.user.service;

import lombok.extern.slf4j.Slf4j;
import net.hyerin.email.service.EmailService;
import net.hyerin.user.domain.User;
import net.hyerin.user.dto.UserSigninDto;
import net.hyerin.user.dto.UserSignupDto;
import net.hyerin.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;

import java.io.Serializable;

import static net.hyerin.user.domain.Role.IS_AUTHENTICATED_FULLY;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signup(UserSignupDto userSignupDto) throws Exception {
        User user = userSignupDto.toEntityWithPasswordEncoder(passwordEncoder);
        emailService.sendMail(user.getEmail());
        userRepository.save(user);
    }

    @Override
    public User signin(UserSigninDto userSigninDto) {
        User user = userRepository.findOneByEmail(userSigninDto.getEmail());
        if(user == null)
            return null;
        if(!passwordEncoder.matches(userSigninDto.getPassword(), user.getPassword()))
            return null;
        return user;
    }

    @Override
    public void updateUserType(String email){
        User user = userRepository.findOneByEmail(email);
        user.setUserType(IS_AUTHENTICATED_FULLY);
        user.setEnabled(true);
    }

    @Override
    @Transactional
    public boolean hasErrors(UserSignupDto userSignupDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return true;

        if (!userSignupDto.getPassword1().equals(userSignupDto.getPassword2())) {
            bindingResult.rejectValue("password2", null, "비밀번호가 일치하지 않습니다.");
            return true;
        }

        User user = userRepository.findOneByEmail(userSignupDto.getEmail());
        if (user != null) {
            if(!user.isEnabled()){
                userRepository.deleteByEmail(user.getEmail());
                return true;
            }
            bindingResult.rejectValue("email", null, "사용자 아이디가 중복됩니다.");
            return true;
        }
        return false;
    }

}
