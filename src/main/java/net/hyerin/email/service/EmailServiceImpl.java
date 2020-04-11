package net.hyerin.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private static final String SENDER = "litebook.official@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendMail(String email) {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            // 메일 전송
            String code = generateVerificationCode();
            helper = new MimeMessageHelper(msg, false, "UTF-8");
            helper.setFrom(SENDER);
            helper.setTo(email);
            helper.setSubject("[litebook] 이메일 인증");
            helper.setText(generateVerificationText(email, code), true);
            javaMailSender.send(msg);

            // <이메일, 인증코드> 레디스에 저장
            redisTemplate.opsForValue().set(email, code);
        } catch(Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public boolean isMatchVerifyCode(String email, String code) {
        if(code.equals(redisTemplate.opsForValue().get(email)))
            return true;
        return false;
    }

    private static String generateVerificationCode() { // 6자리 인증 코드 생성
        return Integer.toString((int)(Math.random() * 899999) + 100000);
    }

    private static String generateVerificationText(String email, String code){
        return "<html><body><p2>인증을 위해 아래 링크를 클릭해 주세요.</p2>" +
                "<p>이메일 인증 후 로그인이 가능합니다.</p>" +
                "<a href='http://localhost:8080/users/email/verify?email=" + email +
                "&code=" + code + "'>" +
                "<p>이 링크를 클릭하세요.</p></body></html>";
    }
}
