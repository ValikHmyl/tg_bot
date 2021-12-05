package com.khmyl.telegram.currency.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.mockito.Mockito.spy;

public abstract class TestWithWebServer {

   protected static MockWebServer mockWebServer = new MockWebServer();
   protected static ObjectMapper objectMapper;

   @BeforeAll
   static void setUp() throws IOException {
      mockWebServer.start();
      objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
   }

   @AfterAll
   static void tearDown() throws IOException {
      mockWebServer.shutdown();
   }

   @Configuration
   static class Config {

      @Bean
      public WebClient webClient() {
         return spy(WebClient.builder()
                             .baseUrl("http://localhost:" + mockWebServer.getPort())
                             .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                             .build());
      }
   }
}
