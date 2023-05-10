package com.khmyl.telegram.currency.bot.kafka;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

   @Value(value = "${kafka.topic.subscriber.name}")
   private String subscriberTopic;

   @Autowired
   private KafkaTemplate<String, SubscriberDto> kafkaTemplate;

   public void sendOnSubscribeMessage(SubscriberDto subscriberDto) {
      kafkaTemplate.send(subscriberTopic, subscriberDto);
   }
}
