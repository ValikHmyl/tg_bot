package com.khmyl.telegram.currency.bot.command.executor;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.Response;

public interface CommandExecutor {

   Response executeCommand(Command command);
}
