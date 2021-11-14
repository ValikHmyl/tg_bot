package com.khmyl.telegram.currency.bot.text.message.impl;

import com.khmyl.telegram.currency.bot.text.message.MessageTemplate;
import com.khmyl.telegram.currency.bot.text.message.TemplateResolver;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TextMessageTemplateResolver implements TemplateResolver {

   @Override
   public String resolve(MessageTemplate template) {
      String msg = template.getMessage();
      for (Map.Entry<String, Object> entry : template.getArgs().entrySet()) {
         msg = msg.replace(entry.getKey(), entry.getValue().toString());
      }
      return msg;
   }
}
