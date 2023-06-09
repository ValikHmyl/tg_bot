package com.khmyl.telegram.currency.bot.message.text.impl;

import com.khmyl.telegram.currency.bot.message.text.LocaleProvider;
import com.khmyl.telegram.currency.bot.message.text.MessageTemplate;
import com.khmyl.telegram.currency.bot.message.text.TemplateResolver;
import com.khmyl.telegram.currency.bot.message.text.TextMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocalizedTextMessageProvider implements TextMessageProvider {

   @Autowired
   private TemplateResolver templateResolver;

   @Autowired
   private LocaleProvider localeProvider;

   @Override
   public String getTextMessage(MessageTemplate messageTemplate) {
      return templateResolver.resolve(messageTemplate, localeProvider.getLocaleForCurrentSubscriber());
   }
}
