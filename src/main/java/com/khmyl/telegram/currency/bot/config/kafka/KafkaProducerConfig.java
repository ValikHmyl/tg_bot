package com.khmyl.telegram.currency.bot.config.kafka;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

   @Value(value = "${spring.kafka.bootstrap-servers}")
   private String bootstrapAddress;

   @Bean
   public ProducerFactory<String, SubscriberDto> subscriberProducerFactory() {
      Map<String, Object> configProps = new HashMap<>();
      configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
      configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
      configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
      configProps.put(ProducerConfig.ACKS_CONFIG, "1");
      return new DefaultKafkaProducerFactory<>(configProps);
   }

   @Bean
   public KafkaTemplate<String, SubscriberDto> subscriberKafkaTemplate() {
      return new KafkaTemplate<>(subscriberProducerFactory());
   }
}
