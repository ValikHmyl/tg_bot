package com.khmyl.telegram.currency.bot.deprecated;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Deprecated
//@Component
@Slf4j
public class CommandCurrencyBot extends TelegramLongPollingCommandBot {

   @Value("${bot.name}")
   private String botName;

   @Value("${bot.token}")
   private String token;

   @Autowired
   private IBotCommand[] commands;

   @PostConstruct
   public void setUp() {
      registerAll(commands);
   }

   @Override
   public String getBotUsername() {
      return botName;
   }

   @Override
   public void processNonCommandUpdate(Update update) {
      if (update.hasCallbackQuery()) {
         String callbackData = update.getCallbackQuery().getData();
         getRegisteredCommands().stream().filter(command -> callbackData.contains(command.getCommandIdentifier())).findFirst().ifPresent(command -> {
            Message message = update.getCallbackQuery().getMessage();
            message.setText(callbackData);
            message.setEntities(Collections.singletonList(MessageEntity.builder()
                    .type(EntityType.BOTCOMMAND)
                    .offset(callbackData.indexOf(BotCommand.COMMAND_INIT_CHARACTER))
                    .length(callbackData.length())
                    .build()));
            update.setMessage(message);
            onUpdateReceived(update);
         });

      }
   }

   @Override
   public String getBotToken() {
      return token;
   }
}
