package com.khmyl.telegram.currency.bot.repository;

import com.khmyl.telegram.currency.bot.model.entity.Subscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

//@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Testcontainers
@ActiveProfiles("test-flyway")
public class SubscriberRepositoryTcTest {

   @Autowired
   private SubscriberRepository subscriberRepository;

   @Container
   private static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:15")).withDatabaseName("khmyl_app")
           .withUsername("root")
           .withPassword("root");

   @DynamicPropertySource
   static void registerMySQLProperties(DynamicPropertyRegistry registry) {
      registry.add("spring.datasource.url", postgres::getJdbcUrl);
      registry.add("spring.datasource.username", postgres::getUsername);
      registry.add("spring.datasource.password", postgres::getPassword);
   }

   @Test
   public void testAddSub() {
      Subscriber test = subscriberRepository.saveAndFlush(Subscriber.builder().id(123L).chatId(223L).name("test").build());
      Optional<Subscriber> byId = subscriberRepository.findById(test.getId());
      Assertions.assertTrue(byId.isPresent());
      Assertions.assertEquals(byId.get().getName(), test.getName());
      System.out.println(byId.get());
   }

}
