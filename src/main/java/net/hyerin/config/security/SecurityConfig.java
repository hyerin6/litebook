package net.hyerin.config.security;

import net.hyerin.user.service.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 웹 보안을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyAuthenticationProvider myAuthenticationProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/res/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/users/signup").permitAll()
                .antMatchers("/users/signupSuccess").permitAll()
                .antMatchers("/users/signupFail").permitAll()
                .antMatchers("/users/signin").permitAll()
                .antMatchers("/users/email").permitAll()
                .antMatchers("/users/email/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/**").authenticated();

        http.csrf().disable();

        // TODO JSP 구현 후 작성 예정
        http.formLogin()
                .loginPage("/users/signin")
                .loginProcessingUrl("/users/signin_processing")
                .failureUrl("/users/signin?error")
                .defaultSuccessUrl("/users/profile", true)
                .usernameParameter("email")
                .passwordParameter("password");

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout_processing"))
                .logoutSuccessUrl("/users/signin")
                .invalidateHttpSession(true);

        http.authenticationProvider(myAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
