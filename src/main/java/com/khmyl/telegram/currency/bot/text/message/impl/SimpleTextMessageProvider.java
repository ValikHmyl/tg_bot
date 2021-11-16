package com.khmyl.telegram.currency.bot.text.message.impl;

import com.khmyl.telegram.currency.bot.text.message.MessageTemplate;
import com.khmyl.telegram.currency.bot.text.message.TemplateResolver;
import com.khmyl.telegram.currency.bot.text.message.TextMessageProvider;
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
