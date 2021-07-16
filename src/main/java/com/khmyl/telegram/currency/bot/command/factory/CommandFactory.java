package com.khmyl.telegram.currency.bot.command.factory;

import com.khmyl.telegram.currency.bot.command.Command;

public interface CommandFactory<T> {

   Command defineCommand(T t);

}

