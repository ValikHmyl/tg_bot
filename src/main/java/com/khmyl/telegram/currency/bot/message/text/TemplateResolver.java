package com.khmyl.telegram.currency.bot.message.text;

import java.util.Locale;

public interface TemplateResolver {

   String resolve(MessageTemplate template, Locale locale);
}
