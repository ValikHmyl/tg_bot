package com.khmyl.telegram.currency.bot.kafka;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.quartz.scheduler.SubscriberScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class KafkaSubscriberListener {

   @Autowired
   private SubscriberScheduler subscriberScheduler;

   @KafkaListener(topics = "${kafka.topic.subscriber.name}", containerFactory = "subscriberKafkaListenerContainerFactory")
   public void onSubscribe(SubscriberDto subscriber) {
      subscriberScheduler.scheduleRatesJob(subscriber);
      log.info(LocalDateTime.now() + " " + subscriber);
   }

}
