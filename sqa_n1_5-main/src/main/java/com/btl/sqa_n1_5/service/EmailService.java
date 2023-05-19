package com.btl.sqa_n1_5.service;

public interface EmailService {
  public void sendEmail(String to, String htmlContent, String mailSubject);
}
