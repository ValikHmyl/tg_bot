package com.khmyl.telegram.currency.bot.command.executor;

import com.khmyl.telegram.currency.bot.command.Command;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandExecutor {

   BotApiMethod<Message> executeCommand(Command command);
}
