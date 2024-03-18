package br.com.fiaprestaurant.restaurant.infrastructure.mailer;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.ALTERNATIVE_USER_EMAIL;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class MailerServiceTest {

  private MimeMessage mimeMessage;
  @Mock
  private JavaMailSender mailSender;
  @InjectMocks
  private MailerService mailerService;

  @BeforeEach
  public void before() {
    mimeMessage = new MimeMessage((Session)null);
  }

  @Test
  void shouldSendEmail() {
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    assertThatCode(() -> mailerService.send(DEFAULT_USER_EMAIL, "Title", "Content",
        ALTERNATIVE_USER_EMAIL)).doesNotThrowAnyException();
    verify(mailSender).send(any(MimeMessage.class));
  }

}
