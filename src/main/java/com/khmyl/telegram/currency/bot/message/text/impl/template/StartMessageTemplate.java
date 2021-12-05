package com.khmyl.telegram.currency.bot.message.text.impl.template;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.message.text.MessageTemplate;
import com.khmyl.telegram.currency.bot.message.text.TextMessageConstants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component(TextMessageConstants.START_COMMAND_MESSAGE_KEY)
@Scope("prototype")
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
