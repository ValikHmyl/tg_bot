package com.khmyl.telegram.currency.bot.quartz.job;

import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@ConditionalOnProperty(name = "isHeroku", havingValue = "true")
public class HerokuAwakeJob {

   @Value("${app_name}")
   private String appName;

   @Scheduled(cron = "0 */20 * ? * *")
   public void execute() {
      WebClient.create()
               .get()
               .uri(uriBuilder -> uriBuilder.scheme("http").host("{appName}.herokuapp.com").build(appName))
               .retrieve()
               .toEntity(String.class)
               .block();
   }
}
