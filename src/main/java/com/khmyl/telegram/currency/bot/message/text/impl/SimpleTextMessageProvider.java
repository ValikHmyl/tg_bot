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

   @Autowired
   private MessageTemplateByKeyFactory messageTemplateFactory;

   @Override
   public String getTextMessage(String messageKey, Object... args) {
      return templateResolver.resolve(getTemplateByKey(messageKey, args));
   }

   private MessageTemplate getTemplateByKey(String messageKey, Object... args) {
      return messageTemplateFactory.getTemplate(messageKey, args);
   }

}
