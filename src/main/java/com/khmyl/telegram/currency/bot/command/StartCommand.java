package com.khmyl.telegram.currency.bot.command;

import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.quartz.job.SendRatesJob;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import com.khmyl.telegram.currency.bot.text.message.TextMessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalTime;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Slf4j
@Component
@Scope(SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StartCommand implements Command {

   private final Message message;

   //to props
   private static final LocalTime DEFAULT_TIME = LocalTime.of(7, 0);

   @Autowired
   private SubscriberService subscriberService;

   @Autowired
   private Scheduler scheduler;

   @Autowired
   private TextMessageProvider<SubscriberDto> welcomeTextMessageProvider;

   @Override
   public SendMessage execute() {
      SubscriberDto subscriber = getSubscriber();
      if (!subscriberService.exist(subscriber.getId())) {
         subscriberService.add(subscriber);
      }
      scheduleRatesJob(subscriber);
      return getResponse(subscriber);
   }

   private SendMessage getResponse(SubscriberDto subscriber) {
      return SendMessage.builder()
                        .chatId(subscriber.getChatId().toString())
                        .text(welcomeTextMessageProvider.getTextMessage(subscriber))
                        .parseMode(ParseMode.HTML)
                        .replyMarkup(getKeyboard())
                        .build();
   }

   private SubscriberDto getSubscriber() {
      return SubscriberDto.builder()
                          .name(message.getFrom().getFirstName())
                          .id(message.getFrom().getId())
                          .chatId(message.getChatId())
                          .build();
   }

   private void scheduleRatesJob(SubscriberDto subscriber) {
      try {
         JobDetail jobDetail = getJobDetails(subscriber);
         if (!scheduler.checkExists(jobDetail.getKey())) {
            Trigger trigger = getTrigger(jobDetail);
            scheduler.scheduleJob(jobDetail, trigger);
         }
      } catch (SchedulerException e) {
         log.error("Error during scheduling the job", e);
      }
   }

   private Trigger getTrigger(JobDetail jobDetail) {
      LocalTime time = DEFAULT_TIME;
      return TriggerBuilder.newTrigger()
                           .forJob(jobDetail)
                           .withIdentity(TriggerKey.triggerKey(jobDetail.getKey().getName(), "RatesToSubTrigger"))
                           .startNow()
                           .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(time.getHour(), time.getMinute()))
//                           .withSchedule(CronScheduleBuilder.cronSchedule("0 "+ time.getMinute() +" */2 ? * * *"))
                           .build();
   }

   private JobDetail getJobDetails(SubscriberDto subscriber) {
      return JobBuilder.newJob(SendRatesJob.class)
                       .storeDurably()
                       .withIdentity(JobKey.jobKey(subscriber.getId().toString(), "RatesToSubJob"))
                       .usingJobData("sub_id", subscriber.getId())
                       .build();
   }

   private ReplyKeyboard getKeyboard() {
      KeyboardRow row = new KeyboardRow();
      Currency.getCurrenciesForRate().forEach(currency -> row.add(KeyboardButton.builder().text(currency.getCode()).build()));
      return ReplyKeyboardMarkup.builder().keyboardRow(row).resizeKeyboard(true).build();
   }
}
