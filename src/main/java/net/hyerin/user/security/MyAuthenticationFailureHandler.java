package net.hyerin.user.security;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {

        if(e instanceof ValidationFailedException){
            httpServletResponse.sendRedirect("/users/signin?error=0");
        }
        else if(e instanceof UsernameNotFoundException){
            httpServletResponse.sendRedirect("/users/signin?error=1");
        }
        else if(e instanceof DisabledException) {
            httpServletResponse.sendRedirect("/users/signin?error=2");
        }
        else {
            httpServletResponse.sendRedirect("/users/signin?error=3");
        }

    }

}
