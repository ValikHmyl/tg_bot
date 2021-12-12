package com.khmyl.telegram.currency.bot.command.impl.response;

import com.khmyl.telegram.currency.bot.command.Response;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
@Setter
public class MessageResponse extends Response {

   private SendMessage message;

   public MessageResponse(String chatId) {
      super(chatId);
   }

   public static Response of(SendMessage message) {
      MessageResponse response = new MessageResponse(message.getChatId());
      response.setMessage(message);
      return response;
   }

   @Override
   public void send(AbsSender delegate) throws TelegramApiException {
      delegate.execute(message);
   }
}
