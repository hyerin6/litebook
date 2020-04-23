package net.hyerin.config.security;

import net.hyerin.user.security.MyAuthenticationFailureHandler;
import net.hyerin.user.security.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    private MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    public SecurityConfig(MyAuthenticationFailureHandler myAuthenticationFailureHandler,
                          MyAuthenticationProvider myAuthenticationProvider){
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
        this.myAuthenticationProvider = myAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
    }

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

        http.formLogin()
                .loginPage("/users/signin")
                .loginProcessingUrl("/users/signin_processing")
                .failureHandler(myAuthenticationFailureHandler)
                .defaultSuccessUrl("/users/profile", true)
                .usernameParameter("email")
                .passwordParameter("password");

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout_processing"))
                .logoutSuccessUrl("/users/signin")
                .invalidateHttpSession(true);

        http.csrf().disable();

        http.authenticationProvider(myAuthenticationProvider);
    }

}
