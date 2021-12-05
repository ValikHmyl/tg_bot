package com.khmyl.telegram.currency.bot.message.text;

public interface MessageTemplateFactory {

   MessageTemplate getTemplate(String key, Object... args);
}
