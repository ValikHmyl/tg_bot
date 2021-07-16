package com.khmyl.telegram.currency.bot.command;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {

   BotApiMethod<Message> execute();
}
