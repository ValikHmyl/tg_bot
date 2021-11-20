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
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class SubscriberSchedulerImpl implements SubscriberScheduler {

   @Value("#{T(java.time.LocalTime).parse('${bot.default_publish_time}', T(java.time.format.DateTimeFormatter).ofPattern('HH:mm'))}")
   private LocalTime defaultTime;

   @Autowired
   private Scheduler scheduler;

   @Override
   public void scheduleRatesJob(SubscriberDto subscriber) {
      try {
         JobDetail jobDetail = getRatesToSubJobDetails(subscriber);
         if (!scheduler.checkExists(jobDetail.getKey())) {
            Trigger trigger = getRatesToSubTrigger(jobDetail);
            scheduler.scheduleJob(jobDetail, trigger);
         }
      } catch (SchedulerException e) {
         log.error("Error during scheduling the job", e);
      }
   }

   @Override
   public void deleteSubJobs(SubscriberDto subscriber) {
      try {
         scheduler.deleteJobs(getSubJobKeys(subscriber));
      } catch (SchedulerException e) {
         log.error("Error during deleting sub jobs", e);
      }
   }

   private Trigger getRatesToSubTrigger(JobDetail jobDetail) {
      return TriggerBuilder.newTrigger()
                           .forJob(jobDetail)
                           .withIdentity(TriggerKey.triggerKey(jobDetail.getKey().getName(), jobDetail.getKey().getGroup()))
                           .startNow()
                           .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(defaultTime.getHour(), defaultTime.getMinute()))
//                           .withSchedule(CronScheduleBuilder.cronSchedule("0 "+ time.getMinute() +" */2 ? * * *"))
                           .build();
   }

   private JobDetail getRatesToSubJobDetails(SubscriberDto subscriber) {
      return JobBuilder.newJob(SendRatesJob.class)
                       .storeDurably()
                       .withIdentity(getRatesToSubJobKey(subscriber))
                       .usingJobData("sub_id", subscriber.getId())
                       .build();
   }

   public List<JobKey> getSubJobKeys(SubscriberDto subscriber) {
      return Collections.singletonList(getRatesToSubJobKey(subscriber));
   }

   private JobKey getRatesToSubJobKey(SubscriberDto subscriber) {
      return JobKey.jobKey(subscriber.getId().toString(), "RatesToSubJob");
   }

}
