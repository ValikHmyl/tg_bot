package com.khmyl.telegram.currency.bot.quartz.job;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.GetRateCommand;
import com.khmyl.telegram.currency.bot.command.executor.CommandExecutor;
import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import com.khmyl.telegram.currency.bot.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Slf4j
@Component
@Scope(SCOPE_PROTOTYPE)
public class SendRatesJob implements Job {

   @Autowired
   private AbsSender absSender;

   @Autowired
   private SubscriberService subscriberService;

   @Autowired
   private CommandExecutor commandExecutor;

   @Override
   public void execute(JobExecutionContext context) throws JobExecutionException {
      long id = context.getJobDetail().getJobDataMap().getLongValue("sub_id");
      subscriberService.getById(id).ifPresent(subscriber -> {
         log.info("Execute Send Rates Job for " + subscriber.getName());
         Currency.getCurrenciesForRate().forEach(currency -> {
            Command getRateCommand = getCommand(subscriber, currency);
            BotApiMethod<Message> message = commandExecutor.executeCommand(getRateCommand);
            sendMessage(message);
         });
      });
   }

   private GetRateCommand getCommand(SubscriberDto subscriber, Currency currency) {
      return BeanUtil.getBean(GetRateCommand.class, subscriber.getChatId(), currency, LocalDate.now());
   }

   private void sendMessage(BotApiMethod<Message> message) {
      try {
         absSender.execute(message);
      } catch (TelegramApiException e) {
         log.error("Unexpected telegram error", e);
      }
   }
}
