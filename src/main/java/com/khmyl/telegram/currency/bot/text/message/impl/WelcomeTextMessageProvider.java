package com.khmyl.telegram.currency.bot.text.message.impl;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.text.message.TextMessageProvider;
import org.springframework.stereotype.Component;

@Component
public class WelcomeTextMessageProvider implements TextMessageProvider<SubscriberDto> {

   @Override
   public String getTextMessage(SubscriberDto subscriber) {
      return String.format("Welcome %s", subscriber.getName());
   }
}
