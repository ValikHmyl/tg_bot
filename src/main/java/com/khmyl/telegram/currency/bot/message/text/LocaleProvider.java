package com.khmyl.telegram.currency.bot.message.text;

import java.util.Locale;

public interface LocaleProvider {

   Locale getLocaleForCurrentSubscriber();

}
