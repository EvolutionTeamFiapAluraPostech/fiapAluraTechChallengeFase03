package br.com.fiaprestaurant.restaurant.infrastructure.mailer;

import br.com.fiaprestaurant.FiapRestaurantApplication;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailerService {

  private final JavaMailSender mailSender;

  public MailerService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void send(String from, String subject, String content, String to) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      mimeMessage.setContent(content, "text/html");
      mailSender.send(mimeMessage);
      FiapRestaurantApplication.logger.info("e-mail sent! " + content);
    } catch (MessagingException e) {
      FiapRestaurantApplication.logger.error("Error by sending e-mail!  {}", e.getMessage());
    }
  }

}
