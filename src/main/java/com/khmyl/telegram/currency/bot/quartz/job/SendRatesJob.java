package com.khmyl.telegram.currency.bot.quartz.job;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.impl.GetRateCommand;
import com.khmyl.telegram.currency.bot.command.Response;
import com.khmyl.telegram.currency.bot.command.executor.CommandExecutor;
import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import com.khmyl.telegram.currency.bot.util.BeanUtil;
import com.khmyl.telegram.currency.bot.util.TelegramSenderHelper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

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

   @Autowired
   private TelegramSenderHelper telegramSenderHelper;

   @Override
   public void execute(JobExecutionContext context) throws JobExecutionException {
      long id = context.getJobDetail().getJobDataMap().getLongValue("sub_id");
      subscriberService.getById(id).ifPresent(subscriber -> {
         log.info("Execute Send Rates Job for " + subscriber.getName());
         Currency.getCurrenciesForRate().forEach(currency -> {
            Command getRateCommand = getCommand(subscriber, currency);
            Response message = commandExecutor.executeCommand(getRateCommand);
            telegramSenderHelper.send(absSender, message);
         });
      });
   }

   private GetRateCommand getCommand(SubscriberDto subscriber, Currency currency) {
      return BeanUtil.getBean(GetRateCommand.class, subscriber.getChatId(), currency, LocalDate.now());
   }

}
