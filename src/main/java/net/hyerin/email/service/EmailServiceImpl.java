package net.hyerin.email.service;

import com.fasterxml.jackson.annotation.JsonRawValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import java.time.Duration;

@Slf4j
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String SENDER;

    private JavaMailSender javaMailSender;

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, @Qualifier("stringRedisTemplate") RedisTemplate<String, String> redisTemplate){
        this.javaMailSender = javaMailSender;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void sendMail(String email) throws Exception {
        MimeMessage msg = javaMailSender.createMimeMessage();

        // 메일 전송
        String code = generateVerificationCode();
        MimeMessageHelper helper = new MimeMessageHelper(msg, false, "UTF-8");
        helper.setFrom(SENDER);
        helper.setTo(email);
        helper.setSubject("[litebook] 이메일 인증");
        helper.setText(generateVerificationText(email, code), true);

        javaMailSender.send(msg);

        // <이메일, 인증코드> 레디스에 저장
        redisTemplate.opsForValue().set(email, code, Duration.ofDays(1));
    }

    @Override
    public boolean isMatchVerifyCode(String email, String code) {
        return code.equals(redisTemplate.opsForValue().get(email));
    }

    @Override
    public String generateVerificationCode() { // 6자리 인증 코드 생성
        return Integer.toString((int)(Math.random() * 899999) + 100000);
    }

    @Override
    public String generateVerificationText(String email, String code){
        return "<html><body><h2>인증을 위해 아래 이미지를 클릭해 주세요.</h2>\n"
            + "<p>이메일 인증 후 로그인이 가능합니다.</p>\n"
            + "<a href='http://20.41.76.24:8080/users/email/verify?email=\"" + email + "\n"
            + "\"&code=\"" + code + "\"' target='_blank'>\n"
            + "<img style=\"display: block; margin: 0px auto; width: 500px; padding-top: 100px;\" src=\"https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/authImg.png\"/>\n"
            + "</a>\n"
            + "</body></html>";
    }
}
