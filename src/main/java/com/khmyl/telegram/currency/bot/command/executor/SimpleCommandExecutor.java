package com.khmyl.telegram.currency.bot.command.executor;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.Response;
import org.springframework.stereotype.Component;

@Component
public class SimpleCommandExecutor implements CommandExecutor {

   @Override
   public Response executeCommand(Command command) {
      return command.execute();
   }

}
