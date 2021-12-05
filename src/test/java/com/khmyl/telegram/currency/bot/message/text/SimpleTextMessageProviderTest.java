package com.khmyl.telegram.currency.bot.message.text;

import com.khmyl.telegram.currency.bot.message.text.impl.SimpleTextMessageProvider;
import com.khmyl.telegram.currency.bot.model.dto.NbrbExchangeRate;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
public class SimpleTextMessageProviderTest {

   @Autowired
   private SimpleTextMessageProvider textMessageProvider;

   @Test
   void testGetTextMessageForDifferentKeys() {
      NbrbExchangeRate rate = NbrbExchangeRate.builder()
                                             .rate(new BigDecimal(1))
                                             .amount(123)
                                             .code("USD")
                                             .id(1L)
                                             .date(LocalDate.now())
                                             .build();
      String textMessage = textMessageProvider.getTextMessage(TextMessageConstants.GET_RATE_COMMAND_MESSAGE_KEY, rate);
      Assertions.assertNotNull(textMessage);
      Assertions.assertTrue(textMessage.contains(rate.getCode()));
      Assertions.assertTrue(textMessage.contains(rate.getRate().toString()));
      Assertions.assertTrue(textMessage.contains(rate.getAmount().toString()));
      Assertions.assertTrue(textMessage.contains(rate.getDate().toString()));

      String name = "Valiantsin";
      textMessage = textMessageProvider.getTextMessage(TextMessageConstants.START_COMMAND_MESSAGE_KEY, SubscriberDto.builder().name(
              name).build());
      Assertions.assertNotNull(textMessage);
      Assertions.assertTrue(textMessage.startsWith("Welcome"));
      Assertions.assertTrue(textMessage.contains(name));


   }
}
