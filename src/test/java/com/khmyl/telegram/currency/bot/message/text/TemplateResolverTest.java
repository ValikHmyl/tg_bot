package com.khmyl.telegram.currency.bot.message.text;

import com.khmyl.telegram.currency.bot.message.text.impl.TextMessageTemplateResolver;
import com.khmyl.telegram.currency.bot.message.text.impl.template.GetRateMessageTemplate;
import com.khmyl.telegram.currency.bot.message.text.impl.template.StartMessageTemplate;
import com.khmyl.telegram.currency.bot.model.dto.NbrbExchangeRate;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TemplateResolverTest {

   @MockBean
   private MessageSource messageSource;

   @InjectMocks
   private TextMessageTemplateResolver templateResolver;

//   @BeforeAll
//   public static void setupClass() {
//      templateResolver = new TextMessageTemplateResolver();
//   }

   @BeforeEach
   public void setup() {
      when(messageSource.getMessage(anyString(), any(), any())).thenAnswer((Answer<String>) invocation -> {
         Object[] args = invocation.getArguments();
         ResourceBundle bundle = ResourceBundle.getBundle("text/messages", (Locale) args[2]);
         return bundle.getString((String) args[0]);
      });
      MockitoAnnotations.initMocks(this);

   }

   @Test
   void testGetTextMessageForDifferentKeys() {
      NbrbExchangeRate rate = NbrbExchangeRate.builder().rate(new BigDecimal(1)).amount(123).code("USD").id(1L).date(LocalDate.now()).build();
      String textMessage = templateResolver.resolve(new GetRateMessageTemplate(rate), Locale.getDefault());
      Assertions.assertNotNull(textMessage);
      Assertions.assertTrue(textMessage.contains(rate.getCode()));
      Assertions.assertTrue(textMessage.contains(rate.getRate().toString()));
      Assertions.assertTrue(textMessage.contains(rate.getAmount().toString()));
      Assertions.assertTrue(textMessage.contains(rate.getDate().toString()));

      String name = "Valiantsin";
      textMessage = templateResolver.resolve(new StartMessageTemplate(SubscriberDto.builder().name(name).build()), Locale.getDefault());
      Assertions.assertNotNull(textMessage);
      Assertions.assertTrue(textMessage.startsWith("Welcome"));
      Assertions.assertTrue(textMessage.contains(name));

   }
}
