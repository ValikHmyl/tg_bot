package com.khmyl.telegram.currency.bot.message.text;

public interface TextMessageProvider {

   String getTextMessage(String messageKey, Object ... args);
}
