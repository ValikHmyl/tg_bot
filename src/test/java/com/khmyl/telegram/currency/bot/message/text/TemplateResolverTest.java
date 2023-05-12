package com.khmyl.telegram.currency.bot.message.text;

import com.khmyl.telegram.currency.bot.message.text.impl.TextMessageTemplateResolver;
import com.khmyl.telegram.currency.bot.message.text.impl.template.GetRateMessageTemplate;
import com.khmyl.telegram.currency.bot.message.text.impl.template.StartMessageTemplate;
import com.khmyl.telegram.currency.bot.model.dto.NbrbExchangeRate;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TemplateResolverTest {

   private static TemplateResolver templateResolver;

   @BeforeAll
   public static void setup() {
      templateResolver = new TextMessageTemplateResolver();
   }

   @Test
   void testGetTextMessageForDifferentKeys() {
      NbrbExchangeRate rate = NbrbExchangeRate.builder().rate(new BigDecimal(1)).amount(123).code("USD").id(1L).date(LocalDate.now()).build();
      String textMessage = templateResolver.resolve(new GetRateMessageTemplate(rate));
      Assertions.assertNotNull(textMessage);
      Assertions.assertTrue(textMessage.contains(rate.getCode()));
      Assertions.assertTrue(textMessage.contains(rate.getRate().toString()));
      Assertions.assertTrue(textMessage.contains(rate.getAmount().toString()));
      Assertions.assertTrue(textMessage.contains(rate.getDate().toString()));

      String name = "Valiantsin";
      textMessage = templateResolver.resolve(new StartMessageTemplate(SubscriberDto.builder().name(name).build()));
      Assertions.assertNotNull(textMessage);
      Assertions.assertTrue(textMessage.startsWith("Welcome"));
      Assertions.assertTrue(textMessage.contains(name));

   }
}
