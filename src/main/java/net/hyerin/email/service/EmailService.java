package net.hyerin.email.service;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public interface EmailService {
    public void sendMail(String email) throws Exception;
    public boolean isMatchVerifyCode(String email, String code);
    public String generateVerificationCode();
    public String generateVerificationText(String email, String code);
}
