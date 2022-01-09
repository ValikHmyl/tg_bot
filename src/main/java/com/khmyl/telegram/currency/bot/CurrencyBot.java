package com.khmyl.telegram.currency.bot;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.Response;
import com.khmyl.telegram.currency.bot.command.executor.CommandExecutor;
import com.khmyl.telegram.currency.bot.command.factory.CommandFactory;
import com.khmyl.telegram.currency.bot.util.TelegramSenderHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Slf4j
@Component
public class CurrencyBot extends TelegramLongPollingBot {

   @Value("${bot.name}")
   private String botName;

   @Value("${bot.token}")
   private String token;

   @Autowired
   private CommandExecutor commandExecutor;

   @Autowired
   private CommandFactory<Message> commandFactory;

   @Autowired
   private TelegramSenderHelper telegramSenderHelper;

   @Override
   public String getBotUsername() {
      return botName;
   }

   @Override
   public String getBotToken() {
      return token;
   }

   @Override
   public void onUpdateReceived(Update update) {
      log.info("Update " + update.getUpdateId());
      if (update.hasMessage()) {
         Message message = update.getMessage();
         Command command = commandFactory.defineCommand(message);
         Response responseMessage = commandExecutor.executeCommand(command);
         telegramSenderHelper.send(this, responseMessage);
      }
   }

}
