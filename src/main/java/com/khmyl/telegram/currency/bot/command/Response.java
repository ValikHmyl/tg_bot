package com.khmyl.telegram.currency.bot.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class Response {

   private final Long chatId;

   protected Response(String chatId) {
      this.chatId = Long.parseLong(chatId);
   }

   public abstract void send(AbsSender delegate) throws TelegramApiException;
}
