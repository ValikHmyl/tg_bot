package com.khmyl.telegram.currency.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableScheduling
public class CurrencyBotApplication {

   public static void main(String[] args) {
      SpringApplication.run(CurrencyBotApplication.class, args);
   }

   @GetMapping("/")
   public String home() {
      return "Hello World!";
   }
}
