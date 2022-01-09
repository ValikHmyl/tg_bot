package com.khmyl.telegram.currency.bot.command;

public interface Command {

   String REPORT_COMMAND = "Report";
   String START_COMMAND = "/start";

   Response execute();
}
