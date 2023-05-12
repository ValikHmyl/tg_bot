package com.khmyl.telegram.currency.bot.command.impl;

import com.khmyl.telegram.currency.bot.command.impl.response.MessageResponse;
import com.khmyl.telegram.currency.bot.kafka.KafkaSender;
import com.khmyl.telegram.currency.bot.message.text.TextMessageProvider;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class StartCommandTest {

   @InjectMocks
   private StartCommand underTest;

   @MockBean
   private KafkaSender kafkaSender;

   @MockBean
   private SubscriberService subscriberService;

   @MockBean
   private TextMessageProvider textMessageProvider;

   @Mock
   private Message message;

   @Mock
   private User user;

   @BeforeEach
   public void setup() {
      underTest = getStartCommand();
      when(message.getFrom()).thenReturn(user);
      MockitoAnnotations.initMocks(this);
   }

   @Test
   public void test() {
      when(textMessageProvider.getTextMessage(any())).thenReturn("test");

      MessageResponse response = (MessageResponse) underTest.execute();

      Assertions.assertNotNull(response);
      Assertions.assertNotNull(response.getMessage().getReplyMarkup());
      verify(subscriberService).add(any(SubscriberDto.class));
      verify(kafkaSender).sendOnSubscribeMessage(any(SubscriberDto.class));
   }

   private StartCommand getStartCommand() {
      return new StartCommand(message);
   }
}
