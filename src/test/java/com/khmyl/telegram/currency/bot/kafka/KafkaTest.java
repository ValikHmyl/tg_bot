package com.khmyl.telegram.currency.bot.kafka;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.quartz.scheduler.SubscriberScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@DirtiesContext
@Testcontainers
@SpringBootTest
//@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ActiveProfiles("test")
public class KafkaTest {

   @Container
   public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

   @Autowired
   private KafkaProducer producer;

   @SpyBean
   private KafkaSubscriberListener consumer;

   @Captor
   private ArgumentCaptor<SubscriberDto> subscriberDtoArgumentCaptor;

   @MockBean
   private SubscriberScheduler scheduler;

   @DynamicPropertySource
   static void kafkaProperties(DynamicPropertyRegistry registry) {
      registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
   }

   @Test
   public void testKafka() {

      SubscriberDto subscriberDto = SubscriberDto.builder().id(123L).chatId(321L).name("test").build();
      producer.sendOnSubscribeMessage(subscriberDto);

      verify(consumer, timeout(10000).atLeast(1)).onSubscribe(subscriberDtoArgumentCaptor.capture());
      Assertions.assertEquals(subscriberDtoArgumentCaptor.getValue().getId(), subscriberDto.getId());

   }
}
