package com.khmyl.telegram.currency.bot.config.kafka;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

   @Value(value = "${spring.kafka.bootstrap-servers}")
   private String bootstrapAddress;

   @Bean
   public ConsumerFactory<String, SubscriberDto> subscriberConsumerFactory() {
      Map<String, Object> props = new HashMap<>();
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "subs-group-1");
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
      props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
      props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
      return new DefaultKafkaConsumerFactory<>(
              props,
              new StringDeserializer(),
              new JsonDeserializer<>(SubscriberDto.class));
   }

   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, SubscriberDto> subscriberKafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, SubscriberDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(subscriberConsumerFactory());
      return factory;
   }
}
