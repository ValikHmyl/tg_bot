package com.khmyl.telegram.currency.bot.util;

import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.lang.reflect.Field;
import java.net.HttpURLConnection;

@Slf4j
@Component
public class TelegramSenderHelper {

   @Autowired
   private SubscriberService subscriberService;

   public void sendMessage(AbsSender delegate, BotApiMethod<Message> message) {
      try {
         delegate.execute(message);
      } catch (TelegramApiRequestException ex) {
         if (ex.getErrorCode() == HttpURLConnection.HTTP_FORBIDDEN) {
            log.info("Can't send msg to user: ", ex);
            subscriberService.remove(getChatIdIfExist(message));
         }
      } catch (TelegramApiException ex) {
         log.error("Unexpected telegram error", ex);
      }
   }

   @SneakyThrows
   private Long getChatIdIfExist(Object object) {
      Class<?> objectClass = object.getClass();
      for (Field field : objectClass.getDeclaredFields()) {
         if (field.getName().equals("chatId")) {
            field.setAccessible(true);
            Long result = Long.valueOf(field.get(object).toString());
            field.setAccessible(false);
            return result;
         }
      }
      return null;
   }

}
