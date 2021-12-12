package com.khmyl.telegram.currency.bot.command.impl.response;

import com.khmyl.telegram.currency.bot.command.Response;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
@Setter
public class DocumentResponse extends Response {

   private SendDocument document;

   public DocumentResponse(String chatId) {
      super(chatId);
   }

   public static Response of(SendDocument sendDocument) {
      DocumentResponse response = new DocumentResponse(sendDocument.getChatId());
      response.setDocument(sendDocument);
      return response;
   }

   @Override
   public void send(AbsSender delegate) throws TelegramApiException {
      delegate.execute(document);
   }

}
