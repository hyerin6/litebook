package net.hyerin.user.security;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hyerin.user.dto.UserSigninDto;
import net.hyerin.utils.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.access.vote.AuthenticatedVoter.IS_AUTHENTICATED_FULLY;

// 사용자가 입력한 로그인 아이디와 비밀번호를 검사할 때 사용되는 클래스
@Slf4j
@Component
public class MyAuthenticationProvider implements AuthenticationProvider, Serializable {

    private CustomUserDetailsService userDetailsService;

    private ValidatorFactory validatorFactory;


    @Autowired
    public MyAuthenticationProvider(CustomUserDetailsService userDetailsService, ValidatorFactory validatorFactory){
        this.userDetailsService = userDetailsService;
        this.validatorFactory = validatorFactory;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        return authenticate(email, password);
    }

    public Authentication authenticate(String email, String password) throws AuthenticationException {
        UserSigninDto userSigninDto = UserSigninDto.builder()
                .email(email)
                .password(password)
                .build();

        // 사용자가 입력한 form data 형식이 맞는지 검사
        Validator validator = validatorFactory.getValidator();

        validator.validate(userSigninDto).stream().forEach(x -> {
            throw new ValidationFailedException(x.getMessage());
        });

        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

        if(user == null)
            throw new UsernameNotFoundException(email);

        // 비활성화, 이메일 인증하지 않은 유저
        if(!user.isEnabled()) {
            throw new DisabledException(email);
        }

        // 이메일, 비밀번호 불일치
        String pw = EncryptionUtils.encryptSHA256(userSigninDto.getPassword());
        if(!pw.equals(user.getPassword())) {
            throw new BadCredentialsException(email);
        }

        // 권한 부여
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(IS_AUTHENTICATED_FULLY));

        // 로그인 성공시 로그인 사용자 정보 반환
        return new UsernamePasswordAuthenticationToken(email, password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
