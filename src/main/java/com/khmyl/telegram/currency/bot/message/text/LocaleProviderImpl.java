package com.khmyl.telegram.currency.bot.message.text;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleProviderImpl implements LocaleProvider {

   @Override
   public Locale getLocaleForCurrentSubscriber() {
      return Locale.getDefault();
   }
}
