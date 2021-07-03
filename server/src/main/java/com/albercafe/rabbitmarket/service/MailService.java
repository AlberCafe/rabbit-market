package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.NotificationEmail;
import com.albercafe.rabbitmarket.exception.RabbitMarketException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom("veloci1024@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody(), true);
        };

        try {
            mailSender.send(messagePreparator);
            log.info("활성화 메일이 보내졌다");
        } catch (MailException e) {
            log.error(String.valueOf(e));
            throw new RabbitMarketException("Exception occured when sending mail to " + notificationEmail.getRecipient());
        }
    }
}
