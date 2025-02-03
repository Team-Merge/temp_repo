package com.project_merge.jigu_travel.api.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${front.host}")
    private String frontHost;

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        String subject = "비밀번호 재설정 요청";
        String resetUrl = frontHost +"/auth/reset-password?token=" + resetToken; /**📌서버주소로 수정 예정 **/
        String message = "<h3>비밀번호 재설정을 요청하셨습니다.</h3>"
                + "<p>아래 링크를 클릭하여 비밀번호를 재설정하세요:</p>"
                + "<a href=\"" + resetUrl + "\">비밀번호 재설정</a>"
                + "<p>이 링크는 30분 동안 유효합니다.</p>";

        sendEmail(toEmail, subject, message);
    }

    private void sendEmail(String toEmail, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(message, true); // HTML 형식 사용

            mailSender.send(mimeMessage);
            System.out.println("이메일 전송 성공: " + toEmail);
        } catch (MessagingException e) {
            System.err.println("이메일 전송 실패: " + e.getMessage());
            throw new RuntimeException("이메일 전송 중 오류 발생");
        }
    }
}