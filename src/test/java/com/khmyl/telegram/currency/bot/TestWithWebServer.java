package com.khmyl.telegram.currency.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

public abstract class TestWithWebServer {

   protected static MockWebServer mockWebServer;
   protected static ObjectMapper objectMapper;

   @Value("${mock.server.port:8081}")
   private int port;

   @PostConstruct
   private void setUp() throws IOException {
      mockWebServer = new MockWebServer();
      mockWebServer.start(port);
      objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
   }

   @PreDestroy
   private void tearDown() throws IOException {
      mockWebServer.shutdown();
   }

}
