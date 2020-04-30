package net.hyerin.user.security;

import net.hyerin.user.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class MyAuthenticaion extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private User user;

    public MyAuthenticaion (String loginId, String password, List<GrantedAuthority> grantedAuthorities, User user) {
        super(loginId, password, grantedAuthorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
