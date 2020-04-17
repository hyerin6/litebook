package net.hyerin.user.security;

import lombok.extern.slf4j.Slf4j;
import net.hyerin.user.domain.User;
import net.hyerin.user.dto.UserSigninDto;
import net.hyerin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.access.vote.AuthenticatedVoter.IS_AUTHENTICATED_FULLY;

// 사용자가 입력한 로그인 아이디와 비밀번호를 검사할 때 사용되는 클래스
@Component
@Slf4j
public class MyAuthenticationProvider implements AuthenticationProvider, Serializable {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        return authenticate(email, password);
    }

    public Authentication authenticate(String email, String password) throws AuthenticationException {
        UserSigninDto userSigninDto = UserSigninDto.builder()
                .email(email)
                .password(password)
                .build();

       User user = userService.signin(userSigninDto);

        // 가입된 유저가 아니거나 이메일 인증이 완료되지 않은 유저
        if (user == null) return null;
        if(!user.isEnabled()) return null;

        // 권한 부여
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(IS_AUTHENTICATED_FULLY));

        // 로그인 성공시 로그인 사용자 정보 반환
        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .userType(user.getUserType())
                .enabled(user.isEnabled())
                .build();
        return new UsernamePasswordAuthenticationToken(email, password, customUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public class MyAuthenticaion extends UsernamePasswordAuthenticationToken implements Serializable {
        private static final long serialVersionUID = 1L;
        User user;

        public MyAuthenticaion (String email, String password, List<GrantedAuthority> grantedAuthorities, User user) {
            super(email, password, grantedAuthorities);
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

    }

}
