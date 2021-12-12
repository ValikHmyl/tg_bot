package com.khmyl.telegram.currency.bot.command.impl;

import com.khmyl.telegram.currency.bot.command.impl.response.MessageResponse;
import com.khmyl.telegram.currency.bot.message.text.TextMessageProvider;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.quartz.scheduler.SubscriberScheduler;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import com.khmyl.telegram.currency.bot.util.BeanUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class StartCommandTest {

   private StartCommand underTest;

   @MockBean
   private SubscriberScheduler subscriberScheduler;

   @MockBean
   private SubscriberService subscriberService;

   @MockBean
   private TextMessageProvider textMessageProvider;

   @Mock
   private Message message;

   @Mock
   private User user;

   @Mock
   private SubscriberDto subscriber;

   @Autowired
   private ApplicationContext applicationContext;

   @BeforeEach
   public void setup() {
      underTest = getStartCommand();
   }

   @Test
   public void test() {
      when(textMessageProvider.getTextMessage(any(), any())).thenReturn("test");
      when(message.getFrom()).thenReturn(user);

      MessageResponse response = (MessageResponse) underTest.execute();

      Assertions.assertNotNull(response);
      Assertions.assertNotNull(response.getMessage().getReplyMarkup());
      verify(subscriberService).add(any(SubscriberDto.class));
      verify(subscriberScheduler).scheduleRatesJob(any(SubscriberDto.class));
   }

   private StartCommand getStartCommand() {
      return BeanUtil.getBean(StartCommand.class, message);
   }
}
