package com.khmyl.telegram.currency.bot.quartz.job;

import lombok.SneakyThrows;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@ConditionalOnProperty(name = "isHeroku", havingValue = "true")
public class HerokuAwakeJob implements Job {

   @Value("${app_name}")
   private String appName;

   @Value("${bot.heroku.awake.cron}")
   private String awakeJobCronExpression;

   @Autowired
   private Scheduler scheduler;

   @Override
   public void execute(JobExecutionContext context)  {
      WebClient.create()
               .get()
               .uri(uriBuilder -> uriBuilder.scheme("http").host("{appName}.herokuapp.com").build(appName))
               .retrieve()
               .toEntity(String.class)
               .block();
   }

   @SneakyThrows
   @EventListener
   public void onApplicationEvent(ContextRefreshedEvent event) {
      JobKey jobKey = JobKey.jobKey(this.getClass().getSimpleName(), "Heroku");
      scheduler.deleteJob(jobKey);
      JobDetail jobDetail = JobBuilder.newJob(this.getClass())
                                      .storeDurably()
                                      .withIdentity(jobKey)
                                      .build();
      CronTrigger trigger = TriggerBuilder.newTrigger()
                                          .forJob(jobDetail)
                                          .withIdentity(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()))
                                          .startNow()
                                          .withSchedule(CronScheduleBuilder.cronSchedule(awakeJobCronExpression))
                                          .build();
      scheduler.scheduleJob(jobDetail, trigger);
   }

}
