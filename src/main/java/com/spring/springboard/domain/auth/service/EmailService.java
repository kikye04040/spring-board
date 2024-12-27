package com.spring.springboard.domain.auth.service;

import com.spring.springboard.config.RedisUtil;
import com.spring.springboard.domain.common.enums.ErrorStatus;
import com.spring.springboard.domain.common.exception.ApiException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendEmail(String redisKey, String email) {
        int authNumber = createAuthNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            String body = "";
            body += "<h3> Spring Board 가입 인증 번호입니다. </h3>";
            body += "<h1> " + authNumber + " </h1>";

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(senderEmail); // 보내는 이
            helper.setTo(email); // 받는 이
            helper.setSubject("Spring Board 이메일 인증"); // 이메일 제목
            helper.setText(body, true);
        } catch (MessagingException e) {
            throw new ApiException(ErrorStatus.FAIL_EMAIL_SENDING);
        }

        // 메일 전송
        javaMailSender.send(message);

        // redis 저장
        redisUtil.authEmail(redisKey, authNumber);
    }

    public int createAuthNumber() { return 100000 + ThreadLocalRandom.current().nextInt(900000); }
}
