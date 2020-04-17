package net.hyerin.test;

import lombok.extern.slf4j.Slf4j;
import net.hyerin.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    EmailService emailService;

    // 로그인 화면 확인을 위한 메소드
    @GetMapping("signin")
    public String signin() {
        log.info("users/signin");
        return "users/signin";
    }

    // 회원가입 화면 확인을 위한 메소드
    @GetMapping("signup")
    public String signup() {
        log.info("users/signup");
        return "users/signup";
    }

    // 프로필 화면 확인을 위한 메소드
    @GetMapping("profile")
    public String profile() {
        log.info("users/profile");
        return "users/profile";
    }

}
