package com.khmyl.telegram.currency.bot.text.message;

public interface TextMessageProvider<T> {

   String getTextMessage(T t);
}
