package net.hyerin.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hyerin.email.service.EmailService;
import net.hyerin.user.domain.User;
import net.hyerin.user.dto.UserSigninDto;
import net.hyerin.user.dto.UserSignupDto;
import net.hyerin.user.repository.UserRepository;
import net.hyerin.user.security.CustomUserDetailsService;
import net.hyerin.utils.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import sun.security.util.Password;

import static net.hyerin.user.domain.Role.IS_AUTHENTICATED_FULLY;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void signup(UserSignupDto userSignupDto) throws Exception {
        User user = userSignupDto.toEntityWithPasswordEncoder(EncryptionUtils.encryptSHA256(userSignupDto.getPassword1()));
        emailService.sendMail(user.getEmail());
        userRepository.save(user);
    }

    @Override
    @Transactional
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
