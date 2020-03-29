package net.hyerin.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    // 로그인 화면 확인을 위한 메소드
    @GetMapping("signin")
    public String signin() {
        log.info("guest/signin");
        return "guest/signin";
    }

    // 회원가입 화면 확인을 위한 메소드
    @GetMapping("signup")
    public String signup() {
        log.info("guest/signup");
        return "guest/signup";
    }

    // 프로필 화면 확인을 위한 메소드
    @GetMapping("profile")
    public String profile() {
        log.info("user/profile");
        return "user/profile";
    }

}
