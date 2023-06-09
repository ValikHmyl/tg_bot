package com.khmyl.telegram.currency.bot.message.text.impl;

import com.khmyl.telegram.currency.bot.message.text.MessageTemplate;
import com.khmyl.telegram.currency.bot.message.text.TemplateResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Component
public class TextMessageTemplateResolver implements TemplateResolver {

   @Autowired
   private MessageSource messageSource;

   @Override
   public String resolve(MessageTemplate template, Locale locale) {
      String msg = messageSource.getMessage(template.getMessageKey(),null, locale);
      for (Map.Entry<String, Object> entry : template.getArgs().entrySet()) {
         msg = msg.replace(entry.getKey(), entry.getValue().toString());
      }
      return msg;
   }
}
