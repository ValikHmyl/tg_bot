package com.khmyl.telegram.currency.bot.quartz.scheduler;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.quartz.job.SendRatesJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@Component
public class SubscriberScheduler {

   @Value("#{T(java.time.LocalTime).parse('${bot.default_publish_time}', T(java.time.format.DateTimeFormatter).ofPattern('HH:mm'))}")
   private LocalTime defaultTime;

   @Autowired
   private Scheduler scheduler;

   public void scheduleRatesJob(SubscriberDto subscriber) {
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
      return TriggerBuilder.newTrigger()
                           .forJob(jobDetail)
                           .withIdentity(TriggerKey.triggerKey(jobDetail.getKey().getName(), "RatesToSubTrigger"))
                           .startNow()
                           .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(defaultTime.getHour(), defaultTime.getMinute()))
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
}
