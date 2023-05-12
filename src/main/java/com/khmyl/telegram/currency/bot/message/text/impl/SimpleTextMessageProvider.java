package com.khmyl.telegram.currency.bot.message.text.impl;

import com.khmyl.telegram.currency.bot.message.text.MessageTemplate;
import com.khmyl.telegram.currency.bot.message.text.TemplateResolver;
import com.khmyl.telegram.currency.bot.message.text.TextMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleTextMessageProvider implements TextMessageProvider {

   @Autowired
   private TemplateResolver templateResolver;

   @Override
   public String getTextMessage(MessageTemplate messageTemplate) {
      return templateResolver.resolve(messageTemplate);
   }
}
