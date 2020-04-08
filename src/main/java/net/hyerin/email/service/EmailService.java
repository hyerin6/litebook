package net.hyerin.email.service;

public interface EmailService {
    public void sendMail(String email);
    public boolean isMatchVerifyCode(String email, String code);
}
