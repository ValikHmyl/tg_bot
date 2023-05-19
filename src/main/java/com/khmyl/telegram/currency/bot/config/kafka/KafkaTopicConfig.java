package com.khmyl.telegram.currency.bot.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaTopicConfig {

   @Value(value = "${kafka.topic.subscriber.name}")
   private String subscriberTopic;

   @Value(value = "${kafka.topic.subscriber.partition}")
   private int subscriberTopicPartition;

   @Value(value = "${kafka.topic.subscriber.replica}")
   private short subscriberTopicReplica;

   @Bean
   public NewTopic subscriberTopic() {
      return new NewTopic(subscriberTopic, subscriberTopicPartition, subscriberTopicReplica);
   }

}
