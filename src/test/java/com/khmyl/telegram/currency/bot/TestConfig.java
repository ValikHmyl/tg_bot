package com.khmyl.telegram.currency.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@Configuration
public class TestConfig {

   @Bean
   public TelegramBotsApi telegramBotsApi() {
      return mock(TelegramBotsApi.class);
   }

   @Bean
   public WebClient webClient(@Value("${mock.server.port:8081}") int port) {
      return spy(WebClient.builder()
                          .baseUrl("http://localhost:" + port)
                          .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                          .build());
   }
}
