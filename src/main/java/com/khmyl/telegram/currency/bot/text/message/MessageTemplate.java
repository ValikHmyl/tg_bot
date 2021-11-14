package com.khmyl.telegram.currency.bot.text.message;

import java.util.Map;

public interface MessageTemplate {

   Map<String, Object> getArgs();

   String getMessage();
}
