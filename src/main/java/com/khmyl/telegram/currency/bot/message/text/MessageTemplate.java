package com.khmyl.telegram.currency.bot.message.text;

import java.util.Map;

public interface MessageTemplate {

   Map<String, Object> getArgs();

   String getMessageKey();
}
