package com.khmyl.telegram.currency.bot.command.executor;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.executor.CommandExecutor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SimpleCommandExecutor implements CommandExecutor {

   @Override
   public BotApiMethod<Message> executeCommand(Command command) {
      return command.execute();
   }

}
