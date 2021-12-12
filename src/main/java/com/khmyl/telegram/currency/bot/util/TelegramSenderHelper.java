package com.khmyl.telegram.currency.bot.util;

import com.khmyl.telegram.currency.bot.command.Response;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.net.HttpURLConnection;

@Slf4j
@Component
public class TelegramSenderHelper {

   @Autowired
   private SubscriberService subscriberService;

   public void send(AbsSender delegate, Response response) {
      try {
         response.send(delegate);
      } catch (TelegramApiRequestException ex) {
         processForbiddenError(response, ex);
      } catch (TelegramApiException ex) {
         log.error("Unexpected telegram error", ex);
      }
   }

   private void processForbiddenError(Response response, TelegramApiRequestException ex) {
      if (ex.getErrorCode() == HttpURLConnection.HTTP_FORBIDDEN) {
         log.info("Can't send msg to user: ", ex);
         subscriberService.remove(response.getChatId());
      }
   }

}
