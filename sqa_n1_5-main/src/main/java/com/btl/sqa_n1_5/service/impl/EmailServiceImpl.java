package com.btl.sqa_n1_5.service.impl;

import com.btl.sqa_n1_5.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  public EmailServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void sendEmail(String to, String htmlContent, String mailSubject) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      helper.setText(htmlContent, true);
      helper.setTo(to);
      helper.setSubject(mailSubject);
      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      e.printStackTrace();
      throw new IllegalStateException("failed to send email");
    }
  }

}
