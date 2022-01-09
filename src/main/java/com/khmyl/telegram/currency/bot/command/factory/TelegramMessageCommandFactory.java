package com.khmyl.telegram.currency.bot.command.factory;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.impl.GetRateCommand;
import com.khmyl.telegram.currency.bot.command.impl.StartCommand;
import com.khmyl.telegram.currency.bot.command.impl.ReportCommand;
import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.util.BeanUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.khmyl.telegram.currency.bot.command.Command.REPORT_COMMAND;
import static com.khmyl.telegram.currency.bot.command.Command.START_COMMAND;

@Component
public class TelegramMessageCommandFactory implements CommandFactory<Message> {

   private final Map<String, Function<Message, Command>> commandsMapper = Collections.unmodifiableMap(new HashMap<>() {
      {
         put(START_COMMAND, message -> BeanUtil.getBean(StartCommand.class, message));
         Currency.getCurrenciesForRate()
                 .forEach(currency -> put(currency.getCode(),
                         message -> BeanUtil.getBean(GetRateCommand.class,
                                 message.getChatId(),
                                 currency,
                                 LocalDate.now())));
         put(REPORT_COMMAND, message -> BeanUtil.getBean(ReportCommand.class, message, 7));
      }
   });

   @Override
   public Command defineCommand(Message message) {
      String command = message.getText();
      if (commandsMapper.containsKey(command)) {
         return commandsMapper.get(command).apply(message);
      }
      throw new IllegalArgumentException("Command not identified");
   }

}
