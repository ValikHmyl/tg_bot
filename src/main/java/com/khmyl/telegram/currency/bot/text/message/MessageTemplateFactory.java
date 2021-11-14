package com.khmyl.telegram.currency.bot.text.message;

public interface MessageTemplateFactory {

   MessageTemplate getTemplate(String key, Object... args);
}
