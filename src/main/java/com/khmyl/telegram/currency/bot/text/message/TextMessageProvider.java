package com.khmyl.telegram.currency.bot.text.message;

public interface TextMessageProvider {

   String getTextMessage(String messageKey, Object ... args);
}
