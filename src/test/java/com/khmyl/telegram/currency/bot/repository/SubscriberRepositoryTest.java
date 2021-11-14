package com.khmyl.telegram.currency.bot.repository;

import com.khmyl.telegram.currency.bot.model.entity.Subscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SubscriberRepositoryTest {

   @Autowired
   private SubscriberRepository subscriberRepository;

   @Test
   public void testRepository() {
      Subscriber subscriber = Subscriber.builder().id(1L).name("testName").chatId(123L).build();
      Subscriber saved = subscriberRepository.save(subscriber);
      Assertions.assertNotNull(saved);
   }
}
