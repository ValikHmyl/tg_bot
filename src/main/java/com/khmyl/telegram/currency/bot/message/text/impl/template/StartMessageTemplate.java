package com.khmyl.telegram.currency.bot.message.text.impl.template;

import com.khmyl.telegram.currency.bot.message.text.MessageTemplate;
import com.khmyl.telegram.currency.bot.message.text.TextMessageConstants;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class StartMessageTemplate implements MessageTemplate {

   private SubscriberDto subscriber;

   @Override
   public Map<String, Object> getArgs() {
      return Map.of(TextMessageConstants.NAME_KEY, subscriber.getName());
   }

   @Override
   public String getMessage() {
      return "Welcome {name}";
   }
}
